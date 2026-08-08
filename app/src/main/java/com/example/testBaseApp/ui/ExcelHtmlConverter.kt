package com.example.testBaseApp.ui

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.ClientAnchor
import org.apache.poi.ss.usermodel.DateUtil
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.Picture
import org.apache.poi.ss.usermodel.PictureData
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.util.CellRangeAddress
import java.util.Base64

object ExcelHtmlConverter {

    // Cấu hình số dòng và cột tối thiểu để vẽ lưới kẻ ô cho đẹp màn hình
    private const val MIN_DISPLAY_ROWS = 20
    private const val MIN_DISPLAY_COLS = 6

    data class SheetInfo(
        val imageMap: Map<String, List<PictureData>>,
        val mergedRegions: List<CellRangeAddress>,
        val mergedRegionMap: Map<String, CellRangeAddress>,
        val globalMaxCols: Int,
        val totalRowsToRender: Int
    )

    fun prepareSheetInfo(sheet: Sheet): SheetInfo {
        val imageMap = extractSheetImages(sheet)
        
        // 1. Xác định dòng cuối thực tế
        val lastRow = sheet.lastRowNum
        val maxImageRow = imageMap.keys.mapNotNull { it.split("_").firstOrNull()?.toIntOrNull() }.maxOrNull() ?: -1
        
        val mergedRegions = mutableListOf<CellRangeAddress>()
        val numMerged = sheet.numMergedRegions
        for (i in 0 until numMerged) {
            mergedRegions.add(sheet.getMergedRegion(i))
        }
        
        val maxMergedRow = mergedRegions.maxOfOrNull { it.lastRow } ?: -1
        val actualDataEnd = maxOf(lastRow, maxImageRow, maxMergedRow)

        // Bù thêm dòng nếu file quá ngắn
        val MIN_ROWS = 40
        val realLastRowNum = maxOf(actualDataEnd, MIN_ROWS - 1)
        val totalRowsToRender = realLastRowNum + 1
        
        val globalMaxCols = calculateGlobalMaxCols(sheet, realLastRowNum, imageMap)

        // 2. Pre-index merged regions để đạt tốc độ O(1) khi render
        val mergedRegionMap = mutableMapOf<String, CellRangeAddress>()
        for (region in mergedRegions) {
            for (r in region.firstRow..region.lastRow) {
                for (c in region.firstColumn..region.lastColumn) {
                    mergedRegionMap["${r}_${c}"] = region
                }
            }
        }

        return SheetInfo(imageMap, mergedRegions, mergedRegionMap, globalMaxCols, totalRowsToRender)
    }

    fun getHtmlHeader(sheet: Sheet, info: SheetInfo): String {
        val html = StringBuilder()
        html.append("<!DOCTYPE html><html><head>")
        html.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
        html.append("<style>")
        html.append(
            """
            body { font-family: sans-serif; margin: 0; padding: 0; background-color: #f8f9fa; }
            table { border-collapse: collapse; table-layout: auto; width: max-content; min-width: 100%; background-color: white; border: 2px solid #888; }
            td, th { border: 1.5px solid #a0a0a0; padding: 8px 12px; font-size: 16px; white-space: pre-wrap; word-break: normal; overflow-wrap: break-word; vertical-align: middle; min-height: 24px; }
            .col-header { background-color: #d8d8d8; text-align: center; font-size: 13px; font-weight: bold; color: #000; position: sticky; top: 0; z-index: 20; border-bottom: 3px solid #666; height: 30px; }
            .excel-img { max-width: 100%; height: auto; display: block; margin: 4px auto; }
            a { color: #1a73e8; text-decoration: underline; }
        """.trimIndent()
        )
        html.append("</style>")
        html.append("""
            <script>
                var isLoading = false;
                var hasMore = true;
                function checkScroll() {
                    if (!hasMore || isLoading) return;
                    var scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight;
                    var scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
                    var clientHeight = window.innerHeight || document.documentElement.clientHeight;
                    if (scrollTop + clientHeight >= scrollHeight - 1500) {
                        isLoading = true;
                        Android.loadMoreRows();
                    }
                }
                window.onscroll = checkScroll;
                function appendRows(html) {
                    if (!html || html.trim() === "") { setHasMore(false); return; }
                    var tbody = document.querySelector('tbody');
                    if (tbody) { 
                        tbody.insertAdjacentHTML('beforeend', html); 
                    }
                    isLoading = false;
                    setTimeout(checkScroll, 500);
                }
                function setHasMore(val) {
                    hasMore = val;
                    isLoading = false;
                    if (!val) { window.onscroll = null; }
                }
            </script>
        """.trimIndent())
        html.append("</head><body><table>")

        // Gợi ý độ rộng từ Excel để giữ khung bảng cân đối
        html.append("<colgroup>")
        for (c in 0 until info.globalMaxCols) {
            val poiWidth = sheet.getColumnWidth(c)
            val widthPx = (poiWidth / 32) + 40
            html.append("<col style=\"min-width: ${widthPx}px;\">")
        }
        html.append("</colgroup>")
        
        // Render tiêu đề cột A, B, C...
        html.append("<thead><tr>")
        for (c in 0 until info.globalMaxCols) {
            html.append("<th class=\"col-header\">${getColumnName(c)}</th>")
        }
        html.append("</tr></thead>")
        html.append("<tbody>")
        
        return html.toString()
    }

    private fun getColumnName(index: Int): String {
        var n = index
        var res = ""
        while (n >= 0) {
            res = ('A' + (n % 26)) + res
            n = n / 26 - 1
        }
        return res
    }

    fun renderRowRange(
        sheet: Sheet,
        startRow: Int,
        endRow: Int,
        info: SheetInfo
    ): String {
        val html = StringBuilder()
        val end = minOf(endRow, info.totalRowsToRender)

        for (rowIndex in startRow until end) {
            val row = sheet.getRow(rowIndex)
            html.append("<tr>")
            
            for (colIndex in 0 until info.globalMaxCols) {
                val key = "${rowIndex}_${colIndex}"
                val mergedRegion = info.mergedRegionMap[key]

                if (mergedRegion != null) {
                    val isTopLeft = (rowIndex == mergedRegion.firstRow && colIndex == mergedRegion.firstColumn)
                    if (isTopLeft) {
                        val rowSpan = mergedRegion.lastRow - mergedRegion.firstRow + 1
                        val colSpan = mergedRegion.lastColumn - mergedRegion.firstColumn + 1
                        val cell = row?.getCell(colIndex)
                        html.append("<td")
                        if (rowSpan > 1) html.append(" rowspan=\"$rowSpan\"")
                        if (colSpan > 1) html.append(" colspan=\"$colSpan\"")
                        html.append(getInlineStyle(cell))
                        html.append(">")
                        html.append(renderCellContent(cell, rowIndex, colIndex, info.imageMap))
                        html.append("</td>")
                    }
                } else {
                    val cell = row?.getCell(colIndex)
                    html.append("<td${getInlineStyle(cell)}>")
                    html.append(renderCellContent(cell, rowIndex, colIndex, info.imageMap))
                    html.append("</td>")
                }
            }
            html.append("</tr>")
        }
        return html.toString()
    }

    fun getHtmlFooter(): String {
        return "</tbody></table></body></html>"
    }

    fun convertToHtml(workbook: Workbook, tab: Int): String {
        val sheet = workbook.getSheetAt(tab) ?: return ""
        val info = prepareSheetInfo(sheet)
        
        val html = StringBuilder()
        html.append(getHtmlHeader(sheet, info))
        html.append(renderRowRange(sheet, 0, info.totalRowsToRender, info))
        html.append(getHtmlFooter())

        return html.toString()
    }

    // Tính toán số cột chung cho toàn bộ bảng để các dòng đồng nhất kẻ ô
    private fun calculateGlobalMaxCols(
        sheet: Sheet,
        realLastRowNum: Int,
        imageMap: Map<String, List<PictureData>>
    ): Int {
        var maxCols = MIN_DISPLAY_COLS

        // 1. Kiểm tra lastCellNum của từng dòng thực tế
        for (row in sheet) {
            if (row.rowNum > realLastRowNum) continue
            val lastCell = row.lastCellNum.toInt()
            if (lastCell > maxCols) maxCols = lastCell
        }

        // 2. Quan trọng: Kiểm tra giới hạn của các vùng gộp (Merged Regions)
        // Nếu một ô gộp kéo dài ra xa hơn các ô dữ liệu, ta phải tính theo nó để không lệch cột
        for (i in 0 until sheet.numMergedRegions) {
            val region = sheet.getMergedRegion(i)
            if (region.firstRow <= realLastRowNum) {
                val lastCol = region.lastColumn + 1
                if (lastCol > maxCols) maxCols = lastCol
            }
        }

        // 3. Kiểm tra cột chứa ảnh
        imageMap.keys.forEach { key ->
            val col = key.split("_").getOrNull(1)?.toIntOrNull()?.plus(1) ?: 0
            if (col > maxCols) maxCols = col
        }

        return maxCols
    }

    // Lấy danh sách ảnh và ánh xạ theo khóa "Row_Col"
    private fun extractSheetImages(sheet: Sheet): Map<String, List<PictureData>> {
        val imageMap = mutableMapOf<String, MutableList<PictureData>>()
        val drawing = sheet.drawingPatriarch ?: return imageMap

        for (shape in drawing) {
            if (shape is Picture) {
                val anchor = shape.clientAnchor
                if (anchor is ClientAnchor) {
                    val key = "${anchor.row1}_${anchor.col1.toInt()}"
                    imageMap.getOrPut(key) { mutableListOf() }.add(shape.pictureData)
                }
            }
        }
        return imageMap
    }

    // Hiển thị nội dung Text kèm theo Hình ảnh (nếu có)
    private fun renderCellContent(
        cell: Cell?,
        rowIndex: Int,
        colIndex: Int,
        imageMap: Map<String, List<PictureData>>
    ): String {
        val sb = StringBuilder()

        // 1. Trích xuất Text
        val textValue = getCellValueAsString(cell)
        if (textValue.isNotEmpty()) {
            sb.append(textValue)
        }

        // 2. Chèn Ảnh dưới dạng Base64
        val key = "${rowIndex}_${colIndex}"
        imageMap[key]?.forEach { picData ->
            val base64 = Base64.getEncoder().encodeToString(picData.data)
            val mimeType = picData.mimeType ?: "image/png"
            if (sb.isNotEmpty()) sb.append("<br>")
            sb.append("<img class=\"excel-img\" src=\"data:$mimeType;base64,$base64\" />")
        }

        return sb.toString()
    }

    private fun getRealLastRowNum(
        sheet: Sheet,
        mergedRegions: List<CellRangeAddress>,
        imageMap: Map<String, List<PictureData>>
    ): Int {
        var lastDataRow = -1

        // Duyệt iterator nhanh hơn nhiều so với getRow(i) lặp ngược khi file có hàng triệu dòng ảo
        for (row in sheet) {
            if (!isRowEmpty(row)) {
                lastDataRow = maxOf(lastDataRow, row.rowNum)
            }
        }

        val maxImageRow = imageMap.keys.mapNotNull { key ->
            key.split("_").firstOrNull()?.toIntOrNull()
        }.maxOrNull() ?: -1

        val maxMergedRow = mergedRegions.map { it.lastRow }.maxOrNull() ?: -1
        
        val result = maxOf(lastDataRow, maxImageRow, maxMergedRow)
        return if (result == -1) 0 else result
    }

    private fun isRowEmpty(row: Row): Boolean {
        // Duyệt qua các cell thực tế có trong row
        for (cell in row) {
            if (cell.cellType != CellType.BLANK) {
                if (cell.cellType == CellType.STRING) {
                    if (cell.stringCellValue.trim().isNotEmpty()) return false
                } else {
                    return false
                }
            }
        }
        return true
    }

    private fun getInlineStyle(cell: Cell?): String {
        if (cell == null) return ""
        val style = cell.cellStyle ?: return ""
        val css = StringBuilder()

        when (style.alignment) {
            HorizontalAlignment.CENTER -> css.append("text-align: center; ")
            HorizontalAlignment.RIGHT -> css.append("text-align: right; ")
            HorizontalAlignment.LEFT -> css.append("text-align: left; ")
            else -> {}
        }

        return if (css.isNotEmpty()) " style=\"$css\"" else ""
    }

    private fun getCellValueAsString(cell: Cell?): String {
        if (cell == null) return ""
        val value = when (cell.cellType) {
            CellType.STRING -> cell.stringCellValue
            CellType.NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    cell.dateCellValue.toString()
                } else {
                    val num = cell.numericCellValue
                    if (num == num.toLong().toDouble()) num.toLong().toString() else num.toString()
                }
            }

            CellType.BOOLEAN -> cell.booleanCellValue.toString()
            CellType.FORMULA -> {
                try {
                    val num = cell.numericCellValue
                    if (num == num.toLong().toDouble()) num.toLong().toString() else num.toString()
                } catch (e: Exception) {
                    try {
                        cell.stringCellValue ?: ""
                    } catch (ex: Exception) {
                        ""
                    }
                }
            }

            else -> ""
        }

        val escaped = escapeHtml(value)
        val linkified = linkify(escaped)
        return linkified.replace("\n", "<br>")
    }

    private fun linkify(text: String): String {
        val urlPattern = Regex("(https?://[^\\s<]+)")
        return urlPattern.replace(text) { result ->
            val url = result.value
            "<a href=\"$url\">$url</a>"
        }
    }

    private fun escapeHtml(s: String): String {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
            .replace("\"", "&quot;").replace("'", "&#39;")
    }
}