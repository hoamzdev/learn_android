package com.example.testBaseApp.ui

import android.util.Xml
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.DateUtil
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy
import org.apache.poi.ss.usermodel.Sheet
import org.xmlpull.v1.XmlPullParser
import java.io.BufferedInputStream
import java.io.Closeable
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.zip.ZipFile

class StreamingExcelReader(
    private val file: File
) : Closeable {

    // ============================================================
    // DATA
    // ============================================================

    data class SheetInfo(
        val name: String,
        val path: String
    )

    data class MergeRegion(
        val firstRow: Int,
        val lastRow: Int,
        val firstCol: Int,
        val lastCol: Int
    ) {
        fun contains(row: Int, col: Int): Boolean {
            return row in firstRow..lastRow && col in firstCol..lastCol
        }

        fun startsAt(row: Int, col: Int): Boolean {
            return row == firstRow && col == firstCol
        }

        fun intersectsRows(start: Int, endExclusive: Int): Boolean {
            return firstRow < endExclusive && lastRow >= start
        }
    }

    data class ColumnInfo(
        val index: Int,
        val width: Float
    )

    data class Cell(
        val row: Int,
        val col: Int,
        val value: String,
        val type: String?
    )

    data class Row(
        val index: Int,
        val cells: List<Cell>
    )

    data class SheetMetadata(
        val merges: List<MergeRegion>,
        val columns: Map<Int, ColumnInfo>,
        val maxColumn: Int,
        val maxRow: Int
    )

    private enum class FileType {
        XLS,
        XLSX
    }

    // ============================================================
    // FILE TYPE
    // ============================================================

    private val fileType = detectFileType(file)

    // ============================================================
    // XLSX STATE
    // ============================================================

    private var zipFile: ZipFile? = null
    private val sharedStrings = mutableListOf<String>()
    private val sheets = mutableListOf<SheetInfo>()

    private var parser: XmlPullParser? = null
    private var sheetInput: BufferedInputStream? = null
    private var currentSheetIndex = -1
    private var currentSheetPath: String? = null
    private var currentRowIndex = 0
    private var currentMetadata: SheetMetadata? = null

    // styleIndex -> format code (ví dụ "m/d/yy", "dd/mm/yyyy")
    private val styleFormats = mutableMapOf<Int, String>()
    private var use1904Dates = false

    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val dateTimeFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    // ============================================================
    // XLS STATE
    // ============================================================

    private var xlsWorkbook: HSSFWorkbook? = null
    private var xlsFormatter: DataFormatter? = null

    // ============================================================
    // INIT
    // ============================================================

    init {
        when (fileType) {
            FileType.XLSX -> {
                zipFile = ZipFile(file)
                loadSharedStrings()
                loadStyles()
                loadDate1904Flag()
                loadSheets()
            }
            FileType.XLS -> {
                file.inputStream().use { input ->
                    xlsWorkbook = HSSFWorkbook(input)
                }
                xlsFormatter = DataFormatter()
                loadXlsSheets()
            }
        }
    }

    // ============================================================
    // PUBLIC
    // ============================================================

    fun getSheets(): List<SheetInfo> = sheets.toList()

    fun getCurrentMetadata(): SheetMetadata? = currentMetadata

    // ============================================================
    // OPEN SHEET
    // ============================================================

    fun openSheet(index: Int): SheetMetadata {
        require(index in sheets.indices) { "Invalid sheet index: $index" }

        closeCurrentSheet()
        currentSheetIndex = index
        currentRowIndex = 0

        return when (fileType) {
            FileType.XLSX -> {
                val sheet = sheets[index]
                currentSheetPath = sheet.path

                val metadata = readSheetMetadata(sheet.path)
                currentMetadata = metadata

                openSheetParser(sheet.path)
                moveToFirstRow()

                metadata
            }
            FileType.XLS -> {
                val sheet = xlsWorkbook!!.getSheetAt(index)
                val metadata = readXlsSheetMetadata(sheet)
                currentMetadata = metadata
                metadata
            }
        }
    }

    // ============================================================
    // SEQUENTIAL READ
    // ============================================================

    fun readNextRows(limit: Int): List<Row> {
        if (limit <= 0) return emptyList()
        return when (fileType) {
            FileType.XLSX -> readNextRowsXlsx(limit)
            FileType.XLS -> readNextRowsXls(limit)
        }
    }

    // ============================================================
    // RANDOM WINDOW READ
    // ============================================================

    fun readRows(startRow: Int, endRowExclusive: Int): List<Row> {
        require(startRow >= 0) { "startRow must be >= 0" }
        require(endRowExclusive >= startRow) { "endRowExclusive must be >= startRow" }

        if (startRow >= endRowExclusive) return emptyList()
        if (currentSheetIndex < 0) return emptyList()

        return when (fileType) {
            FileType.XLSX -> readRowsXlsx(startRow, endRowExclusive)
            FileType.XLS -> readXlsRows(currentSheetIndex, startRow, endRowExclusive)
        }
    }

    // ============================================================
    // XLSX RANDOM WINDOW (tối ưu sequential)
    // ============================================================

    private fun readRowsXlsx(startRow: Int, endRowExclusive: Int): List<Row> {
        val sheetPath = currentSheetPath ?: return emptyList()

        // Nếu đang ở phía trước và còn parser → chỉ skip tiếp, không reopen
        if (parser != null && startRow >= currentRowIndex) {
            val needSkip = startRow - currentRowIndex
            if (needSkip > 0) {
                skipRows(needSkip)
            }
            return readNextRowsXlsx(endRowExclusive - startRow)
        }

        // Nhảy xa / nhảy ngược → reopen
        reopenSheetParser(sheetPath)

        if (startRow > 0) {
            skipRows(startRow)
        }

        return readNextRowsXlsx(endRowExclusive - startRow)
    }

    // ============================================================
    // XLS SHEETS
    // ============================================================

    private fun loadXlsSheets() {
        val workbook = xlsWorkbook ?: return
        sheets.clear()
        for (index in 0 until workbook.numberOfSheets) {
            sheets.add(
                SheetInfo(
                    name = workbook.getSheetName(index),
                    path = "xls://sheet/$index"
                )
            )
        }
    }

    // ============================================================
    // XLS READ NEXT
    // ============================================================

    private fun readNextRowsXls(limit: Int): List<Row> {
        val workbook = xlsWorkbook ?: return emptyList()
        if (currentSheetIndex !in 0 until workbook.numberOfSheets) return emptyList()

        val sheet = workbook.getSheetAt(currentSheetIndex)
        val result = ArrayList<Row>(limit)
        val lastRow = sheet.lastRowNum

        while (result.size < limit && currentRowIndex <= lastRow) {
            val poiRow = sheet.getRow(currentRowIndex)
            result.add(convertXlsRow(poiRow, currentRowIndex))
            currentRowIndex++
        }
        return result
    }

    // ============================================================
    // XLS RANDOM
    // ============================================================

    private fun readXlsRows(
        sheetIndex: Int,
        startRow: Int,
        endRowExclusive: Int
    ): List<Row> {
        val workbook = xlsWorkbook ?: return emptyList()
        if (sheetIndex !in 0 until workbook.numberOfSheets) return emptyList()

        val sheet = workbook.getSheetAt(sheetIndex)

        if (startRow > sheet.lastRowNum) {
            currentRowIndex = endRowExclusive
            return emptyList()
        }

        val actualEnd = minOf(endRowExclusive, sheet.lastRowNum + 1)
        val result = ArrayList<Row>(actualEnd - startRow)

        for (rowIndex in startRow until actualEnd) {
            val poiRow = sheet.getRow(rowIndex)
            result.add(convertXlsRow(poiRow, rowIndex))
        }

        currentRowIndex = actualEnd
        return result
    }

    // ============================================================
    // CONVERT XLS ROW
    // ============================================================

    private fun convertXlsRow(
        row: org.apache.poi.ss.usermodel.Row?,
        rowIndex: Int
    ): Row {
        if (row == null) {
            return Row(index = rowIndex, cells = emptyList())
        }

        val lastCell = row.lastCellNum.toInt()
        if (lastCell <= 0) {
            return Row(index = rowIndex, cells = emptyList())
        }

        val cells = ArrayList<Cell>(lastCell)
        val formatter = xlsFormatter ?: DataFormatter()

        for (colIndex in 0 until lastCell) {
            val cell = row.getCell(colIndex, MissingCellPolicy.RETURN_BLANK_AS_NULL)
                ?: continue

            val value = try {
                if (cell.cellType == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                    val date = cell.dateCellValue
                    if (date != null) {
                        // Có giờ thì show datetime
                        val hasTime = date.hours != 0 || date.minutes != 0 || date.seconds != 0
                        if (hasTime) dateTimeFormatter.format(date)
                        else dateFormatter.format(date)
                    } else {
                        formatter.formatCellValue(cell)
                    }
                } else {
                    formatter.formatCellValue(cell)
                }
            } catch (_: Exception) {
                when (cell.cellType) {
                    CellType.STRING -> cell.stringCellValue
                    CellType.NUMERIC -> cell.numericCellValue.toString()
                    CellType.BOOLEAN -> cell.booleanCellValue.toString()
                    else -> ""
                }
            }

            cells.add(
                Cell(
                    row = rowIndex,
                    col = colIndex,
                    value = value,
                    type = cell.cellType.name
                )
            )
        }

        return Row(index = rowIndex, cells = cells)
    }

    // ============================================================
    // XLS METADATA
    // ============================================================

    private fun readXlsSheetMetadata(sheet: Sheet): SheetMetadata {
        val merges = ArrayList<MergeRegion>(sheet.numMergedRegions)

        for (index in 0 until sheet.numMergedRegions) {
            val region = sheet.getMergedRegion(index)
            merges.add(
                MergeRegion(
                    firstRow = region.firstRow,
                    lastRow = region.lastRow,
                    firstCol = region.firstColumn,
                    lastCol = region.lastColumn
                )
            )
        }

        var maxColumn = 0
        var lastNonEmptyRow = -1

        for (rowIndex in 0..sheet.lastRowNum) {
            val row = sheet.getRow(rowIndex) ?: continue
            if (row.lastCellNum > 0) {
                maxColumn = maxOf(maxColumn, row.lastCellNum - 1)
            }
            // Kiểm tra có nội dung không
            var hasContent = false
            for (c in 0 until row.lastCellNum) {
                val cell = row.getCell(c, MissingCellPolicy.RETURN_BLANK_AS_NULL)
                if (cell != null) {
                    val v = try {
                        (xlsFormatter ?: DataFormatter()).formatCellValue(cell)
                    } catch (_: Exception) {
                        ""
                    }
                    if (v.isNotBlank()) {
                        hasContent = true
                        break
                    }
                }
            }
            if (hasContent) lastNonEmptyRow = rowIndex
        }

        for (merge in merges) {
            maxColumn = maxOf(maxColumn, merge.lastCol)
            lastNonEmptyRow = maxOf(lastNonEmptyRow, merge.lastRow)
        }

        val columns = HashMap<Int, ColumnInfo>()
        for (col in 0..maxColumn) {
            val width = sheet.getColumnWidth(col).toFloat() / 256f
            columns[col] = ColumnInfo(index = col, width = width)
        }

        val maxRow = maxOf(lastNonEmptyRow + 1, 0)

        return SheetMetadata(
            merges = merges,
            columns = columns,
            maxColumn = maxColumn,
            maxRow = maxRow
        )
    }

    // ============================================================
    // XLSX SHEETS
    // ============================================================

    private fun loadSheets() {
        val zip = zipFile ?: return

        val workbookEntry = zip.getEntry("xl/workbook.xml")
            ?: error("Invalid XLSX: workbook.xml not found")

        val relationships = loadWorkbookRelationships()

        zip.getInputStream(workbookEntry).use { input ->
            val p = Xml.newPullParser()
            p.setInput(input, "UTF-8")

            while (p.eventType != XmlPullParser.END_DOCUMENT) {
                if (p.eventType == XmlPullParser.START_TAG && p.name == "sheet") {
                    val name = p.getAttributeValue(null, "name")
                        ?: "Sheet ${sheets.size + 1}"

                    val rid = p.getAttributeValue(
                        "http://schemas.openxmlformats.org/officeDocument/2006/relationships",
                        "id"
                    )

                    val path = relationships[rid]
                    if (path != null) {
                        sheets.add(SheetInfo(name = name, path = path))
                    }
                }
                p.next()
            }
        }
    }

    // ============================================================
    // WORKBOOK RELATIONSHIPS
    // ============================================================

    private fun loadWorkbookRelationships(): Map<String, String> {
        val zip = zipFile ?: return emptyMap()
        val entry = zip.getEntry("xl/_rels/workbook.xml.rels") ?: return emptyMap()

        val result = HashMap<String, String>()

        zip.getInputStream(entry).use { input ->
            val p = Xml.newPullParser()
            p.setInput(input, "UTF-8")

            while (p.eventType != XmlPullParser.END_DOCUMENT) {
                if (p.eventType == XmlPullParser.START_TAG && p.name == "Relationship") {
                    val id = p.getAttributeValue(null, "Id")
                    val target = p.getAttributeValue(null, "Target")

                    if (id != null && target != null) {
                        val normalized = when {
                            target.startsWith("/xl/") -> target.removePrefix("/")
                            target.startsWith("xl/") -> target
                            else -> "xl/$target"
                        }
                        result[id] = normalized
                    }
                }
                p.next()
            }
        }
        return result
    }

    // ============================================================
    // DATE 1904 FLAG
    // ============================================================

    private fun loadDate1904Flag() {
        val zip = zipFile ?: return
        val entry = zip.getEntry("xl/workbook.xml") ?: return

        zip.getInputStream(entry).use { input ->
            val p = Xml.newPullParser()
            p.setInput(BufferedInputStream(input, 64 * 1024), "UTF-8")

            while (p.eventType != XmlPullParser.END_DOCUMENT) {
                if (p.eventType == XmlPullParser.START_TAG && p.name == "workbookPr") {
                    val date1904 = p.getAttributeValue(null, "date1904")
                    use1904Dates = date1904 == "1" || date1904.equals("true", ignoreCase = true)
                    break
                }
                p.next()
            }
        }
    }

    // ============================================================
    // STYLES (để detect date)
    // ============================================================

    private fun loadStyles() {
        val zip = zipFile ?: return
        val entry = zip.getEntry("xl/styles.xml") ?: return

        // Built-in formats (POI/Excel)
        val builtIn = mapOf(
            14 to "m/d/yy",
            15 to "d-mmm-yy",
            16 to "d-mmm",
            17 to "mmm-yy",
            18 to "h:mm AM/PM",
            19 to "h:mm:ss AM/PM",
            20 to "h:mm",
            21 to "h:mm:ss",
            22 to "m/d/yy h:mm",
            45 to "mm:ss",
            46 to "[h]:mm:ss",
            47 to "mmss.0"
        )

        val numFmts = HashMap<Int, String>()
        numFmts.putAll(builtIn)

        val cellXfs = ArrayList<Int>() // styleIndex -> numFmtId

        zip.getInputStream(entry).use { input ->
            val p = Xml.newPullParser()
            p.setInput(BufferedInputStream(input, 64 * 1024), "UTF-8")

            var insideNumFmts = false
            var insideCellXfs = false

            while (p.eventType != XmlPullParser.END_DOCUMENT) {
                when (p.eventType) {
                    XmlPullParser.START_TAG -> {
                        when (p.name) {
                            "numFmts" -> insideNumFmts = true
                            "cellXfs" -> insideCellXfs = true
                            "numFmt" -> {
                                if (insideNumFmts) {
                                    val id = p.getAttributeValue(null, "numFmtId")?.toIntOrNull()
                                    val code = p.getAttributeValue(null, "formatCode")
                                    if (id != null && code != null) {
                                        numFmts[id] = code
                                    }
                                }
                            }
                            "xf" -> {
                                if (insideCellXfs) {
                                    val numFmtId = p.getAttributeValue(null, "numFmtId")?.toIntOrNull() ?: 0
                                    cellXfs.add(numFmtId)
                                }
                            }
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        when (p.name) {
                            "numFmts" -> insideNumFmts = false
                            "cellXfs" -> insideCellXfs = false
                        }
                    }
                }
                p.next()
            }
        }

        styleFormats.clear()
        for (i in cellXfs.indices) {
            val numFmtId = cellXfs[i]
            val code = numFmts[numFmtId]
            if (code != null) {
                styleFormats[i] = code
            }
        }
    }

    private fun isDateFormat(formatCode: String?): Boolean {
        if (formatCode.isNullOrBlank()) return false
        // Bỏ qua General, number thuần
        val code = formatCode
            .replace("[$-.*?]", "", ignoreCase = true)
            .replace("\\", "")
            .lowercase()

        if (code == "general" || code.matches(Regex("0+(\\.0+)?"))) return false

        // Có y/m/d/h/s → coi là date/time
        return code.contains('y') ||
                code.contains('d') ||
                (code.contains('m') && !code.contains('#')) || // m có thể là month hoặc minute
                code.contains('h') ||
                code.contains('s')
    }

    // ============================================================
    // XLSX METADATA + real max row
    // ============================================================

    private fun readSheetMetadata(path: String): SheetMetadata {
        val zip = zipFile ?: error("XLSX ZipFile is null")
        val entry = zip.getEntry(path) ?: error("Sheet XML not found: $path")

        val merges = ArrayList<MergeRegion>()
        val columns = HashMap<Int, ColumnInfo>()
        var maxRow = 0
        var maxColumn = 0
        var lastNonEmptyRow = -1

        zip.getInputStream(entry).use { input ->
            val p = Xml.newPullParser()
            p.setInput(BufferedInputStream(input, 64 * 1024), "UTF-8")

            var currentRowIndex = -1
            var currentRowHasContent = false

            while (p.eventType != XmlPullParser.END_DOCUMENT) {
                if (p.eventType == XmlPullParser.START_TAG) {
                    when (p.name) {
                        "row" -> {
                            // Kết thúc row trước
                            if (currentRowIndex >= 0 && currentRowHasContent) {
                                lastNonEmptyRow = maxOf(lastNonEmptyRow, currentRowIndex)
                            }
                            currentRowHasContent = false

                            val row = p.getAttributeValue(null, "r")?.toIntOrNull()
                            if (row != null) {
                                maxRow = maxOf(maxRow, row)
                                currentRowIndex = row - 1
                            } else {
                                currentRowIndex = -1
                            }
                        }

                        "c" -> {
                            val ref = p.getAttributeValue(null, "r")
                            if (ref != null) {
                                maxColumn = maxOf(maxColumn, getColumnIndex(ref))
                            }
                        }

                        "v", "t" -> {
                            // Có value → row có nội dung
                            if (currentRowIndex >= 0) {
                                currentRowHasContent = true
                            }
                        }

                        "col" -> {
                            val min = p.getAttributeValue(null, "min")?.toIntOrNull()
                            val max = p.getAttributeValue(null, "max")?.toIntOrNull()
                            val width = p.getAttributeValue(null, "width")?.toFloatOrNull()

                            if (min != null && max != null && width != null) {
                                for (col in min..max) {
                                    val index = col - 1
                                    columns[index] = ColumnInfo(index = index, width = width)
                                    maxColumn = maxOf(maxColumn, index)
                                }
                            }
                        }

                        "mergeCell" -> {
                            val ref = p.getAttributeValue(null, "ref")
                            if (ref != null) {
                                parseMergeReference(ref)?.let {
                                    merges.add(it)
                                    maxRow = maxOf(maxRow, it.lastRow + 1)
                                    maxColumn = maxOf(maxColumn, it.lastCol)
                                    lastNonEmptyRow = maxOf(lastNonEmptyRow, it.lastRow)
                                }
                            }
                        }
                    }
                } else if (p.eventType == XmlPullParser.END_TAG && p.name == "row") {
                    if (currentRowIndex >= 0 && currentRowHasContent) {
                        lastNonEmptyRow = maxOf(lastNonEmptyRow, currentRowIndex)
                    }
                    currentRowHasContent = false
                    currentRowIndex = -1
                }
                p.next()
            }

            // Row cuối
            if (currentRowIndex >= 0 && currentRowHasContent) {
                lastNonEmptyRow = maxOf(lastNonEmptyRow, currentRowIndex)
            }
        }

        // real max row: ưu tiên lastNonEmptyRow, fallback maxRow
        // Nếu lastNonEmptyRow + 1 < maxRow nhiều → cắt trailing empty
        val realMaxRow = if (lastNonEmptyRow >= 0) {
            lastNonEmptyRow + 1
        } else {
            0
        }

        // An toàn: nếu phát hiện trailing empty quá nhiều (>30) thì cắt
        val finalMaxRow = if (maxRow - realMaxRow > 30) {
            realMaxRow
        } else {
            maxOf(realMaxRow, maxRow)
        }

        return SheetMetadata(
            merges = merges,
            columns = columns,
            maxColumn = maxColumn,
            maxRow = finalMaxRow
        )
    }

    // ============================================================
    // OPEN XLSX PARSER
    // ============================================================

    private fun openSheetParser(path: String) {
        val zip = zipFile ?: error("ZipFile is null")
        val entry = zip.getEntry(path) ?: error("Sheet not found: $path")

        sheetInput = BufferedInputStream(zip.getInputStream(entry), 64 * 1024)
        parser = Xml.newPullParser().apply {
            setInput(sheetInput, "UTF-8")
        }
    }

    private fun reopenSheetParser(path: String) {
        closeCurrentSheetParser()
        currentSheetPath = path
        currentRowIndex = 0
        openSheetParser(path)
        moveToFirstRow()
    }

    private fun moveToFirstRow() {
        val p = parser ?: return
        while (p.eventType != XmlPullParser.END_DOCUMENT) {
            if (p.eventType == XmlPullParser.START_TAG && p.name == "row") {
                return
            }
            p.next()
        }
    }

    private fun skipRows(count: Int) {
        if (count <= 0) return
        var skipped = 0
        while (skipped < count) {
            val batch = minOf(500, count - skipped)
            val rows = readNextRowsXlsx(batch)
            if (rows.isEmpty()) break
            skipped += rows.size
        }
    }

    // ============================================================
    // XLSX READ
    // ============================================================

    private fun readNextRowsXlsx(limit: Int): List<Row> {
        val p = parser ?: return emptyList()
        if (limit <= 0) return emptyList()

        val result = ArrayList<Row>(limit)

        while (result.size < limit && p.eventType != XmlPullParser.END_DOCUMENT) {
            if (p.eventType == XmlPullParser.START_TAG && p.name == "row") {
                val row = parseRow(p)
                if (row != null) {
                    result.add(row)
                    currentRowIndex = row.index + 1
                }
                continue
            }
            p.next()
        }
        return result
    }

    // ============================================================
    // PARSE ROW
    // ============================================================

    private fun parseRow(p: XmlPullParser): Row? {
        val rowNumber = p.getAttributeValue(null, "r")?.toIntOrNull() ?: return null
        val rowIndex = rowNumber - 1
        val cells = ArrayList<Cell>()

        var event = p.next()
        while (true) {
            if (event == XmlPullParser.END_TAG && p.name == "row") break

            if (event == XmlPullParser.START_TAG && p.name == "c") {
                parseCell(p)?.let {
                    cells.add(it.copy(row = rowIndex))
                }
            }
            event = p.next()
        }

        return Row(index = rowIndex, cells = cells)
    }

    // ============================================================
    // PARSE CELL (có date)
    // ============================================================

    private fun parseCell(p: XmlPullParser): Cell? {
        val reference = p.getAttributeValue(null, "r") ?: return null
        val type = p.getAttributeValue(null, "t")
        val styleIndex = p.getAttributeValue(null, "s")?.toIntOrNull()
        val column = getColumnIndex(reference)

        var value = ""
        var formula = ""

        var event = p.next()
        while (true) {
            if (event == XmlPullParser.END_TAG && p.name == "c") break

            if (event == XmlPullParser.START_TAG) {
                when (p.name) {
                    "v" -> value = p.nextText()
                    "f" -> formula = p.nextText()
                    "is" -> value = parseInlineString(p)
                }
            }
            event = p.next()
        }

        val finalValue = when (type) {
            "s" -> {
                val index = value.toIntOrNull()
                if (index != null && index in sharedStrings.indices) {
                    sharedStrings[index]
                } else {
                    ""
                }
            }
            "inlineStr" -> value
            "b" -> if (value == "1") "TRUE" else "FALSE"
            else -> {
                // numeric / date / general
                val formatCode = styleIndex?.let { styleFormats[it] }
                val num = value.toDoubleOrNull()

                if (num != null && isDateFormat(formatCode) && DateUtil.isValidExcelDate(num)) {
                    try {
                        val date = DateUtil.getJavaDate(num, use1904Dates)
                        // Có phần giờ?
                        val hasTime = (num % 1.0) != 0.0
                        if (hasTime) dateTimeFormatter.format(date)
                        else dateFormatter.format(date)
                    } catch (_: Exception) {
                        value
                    }
                } else {
                    value
                }
            }
        }

        return Cell(
            row = 0,
            col = column,
            value = finalValue,
            type = if (formula.isNotEmpty()) "formula" else type
        )
    }

    // ============================================================
    // INLINE STRING
    // ============================================================

    private fun parseInlineString(p: XmlPullParser): String {
        val builder = StringBuilder()
        var event = p.next()
        while (true) {
            if (event == XmlPullParser.END_TAG && p.name == "is") break
            if (event == XmlPullParser.START_TAG && p.name == "t") {
                builder.append(p.nextText())
            }
            event = p.next()
        }
        return builder.toString()
    }

    // ============================================================
    // SHARED STRINGS
    // ============================================================

    private fun loadSharedStrings() {
        val zip = zipFile ?: return
        val entry = zip.getEntry("xl/sharedStrings.xml") ?: return

        zip.getInputStream(entry).use { input ->
            val p = Xml.newPullParser()
            p.setInput(BufferedInputStream(input, 64 * 1024), "UTF-8")

            var current = StringBuilder()
            var insideSi = false

            while (p.eventType != XmlPullParser.END_DOCUMENT) {
                when (p.eventType) {
                    XmlPullParser.START_TAG -> {
                        when (p.name) {
                            "si" -> {
                                insideSi = true
                                current = StringBuilder()
                            }
                            "t" -> {
                                if (insideSi) current.append(p.nextText())
                            }
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if (p.name == "si") {
                            sharedStrings.add(current.toString())
                            insideSi = false
                        }
                    }
                }
                p.next()
            }
        }
    }

    // ============================================================
    // MERGE / CELL REF
    // ============================================================

    private fun parseMergeReference(reference: String): MergeRegion? {
        val parts = reference.split(":")
        if (parts.size != 2) return null

        val start = parseCellReference(parts[0]) ?: return null
        val end = parseCellReference(parts[1]) ?: return null

        return MergeRegion(
            firstRow = start.second,
            lastRow = end.second,
            firstCol = start.first,
            lastCol = end.first
        )
    }

    private fun parseCellReference(reference: String): Pair<Int, Int>? {
        val columnPart = StringBuilder()
        val rowPart = StringBuilder()

        for (char in reference) {
            if (char.isLetter()) columnPart.append(char)
            else if (char.isDigit()) rowPart.append(char)
        }

        val row = rowPart.toString().toIntOrNull() ?: return null
        var column = 0
        for (char in columnPart) {
            column = column * 26 + (char.uppercaseChar() - 'A' + 1)
        }
        return Pair(column - 1, row - 1)
    }

    private fun getColumnIndex(reference: String): Int {
        var result = 0
        for (char in reference) {
            if (!char.isLetter()) break
            result = result * 26 + (char.uppercaseChar() - 'A' + 1)
        }
        return result - 1
    }

    // ============================================================
    // FILE TYPE
    // ============================================================

    private fun detectFileType(file: File): FileType {
        // 1) Ưu tiên đọc header thật
        file.inputStream().use { input ->
            val header = ByteArray(8)
            val read = input.read(header)
            if (read >= 4) {
                // ZIP / OOXML (.xlsx, .xlsm, ...)
                // PK\x03\x04
                if (header[0] == 0x50.toByte() && header[1] == 0x4B.toByte()) {
                    return FileType.XLSX
                }
                // OLE2 (.xls)
                // D0 CF 11 E0 A1 B1 1A E1
                if (header[0] == 0xD0.toByte() &&
                    header[1] == 0xCF.toByte() &&
                    header[2] == 0x11.toByte() &&
                    header[3] == 0xE0.toByte()
                ) {
                    return FileType.XLS
                }
            }
        }

        // 2) Fallback theo extension
        return when (file.extension.lowercase()) {
            "xls" -> FileType.XLS
            "xlsx", "xlsm", "xltx", "xltm" -> FileType.XLSX
            else -> throw IllegalArgumentException(
                "Unsupported Excel format: ${file.extension}"
            )
        }
    }

    // ============================================================
    // CLOSE
    // ============================================================

    private fun closeCurrentSheetParser() {
        try {
            sheetInput?.close()
        } catch (_: Exception) {
        }
        sheetInput = null
        parser = null
    }

    private fun closeCurrentSheet() {
        closeCurrentSheetParser()
        currentSheetPath = null
    }

    override fun close() {
        closeCurrentSheet()
        try {
            zipFile?.close()
        } catch (_: Exception) {
        }
        zipFile = null

        try {
            xlsWorkbook?.close()
        } catch (_: Exception) {
        }
        xlsWorkbook = null

        sharedStrings.clear()
        sheets.clear()
        styleFormats.clear()
        currentMetadata = null
        currentSheetIndex = -1
        currentRowIndex = 0
    }
}