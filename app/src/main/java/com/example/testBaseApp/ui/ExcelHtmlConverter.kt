package com.example.testBaseApp.ui

import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellRangeAddress
import java.util.Base64

object ExcelHtmlConverter {

    private const val MIN_DISPLAY_COLS = 6

    data class SheetInfo(
        val imageMap: Map<String, List<PictureData>>,
        val mergedRegions: List<CellRangeAddress>,
        val rowMergedRegions: Map<Int, List<CellRangeAddress>>,
        val globalMaxCols: Int,
        val totalRowsToRender: Int
    )

    fun prepareSheetInfo(sheet: Sheet): SheetInfo {
        val imageMap = extractSheetImages(sheet)
        val numMerged = sheet.numMergedRegions
        val mergedRegions = mutableListOf<CellRangeAddress>()
        val rowMergedRegions = mutableMapOf<Int, MutableList<CellRangeAddress>>()

        var maxMergedRow = -1
        for (i in 0 until numMerged) {
            val region = sheet.getMergedRegion(i)
            mergedRegions.add(region)
            // Index vùng gộp theo từng dòng để truy xuất nhanh O(1)
            for (r in region.firstRow..region.lastRow) {
                rowMergedRegions.getOrPut(r) { mutableListOf() }.add(region)
            }
            if (region.lastRow > maxMergedRow) maxMergedRow = region.lastRow
        }

        val actualDataEnd = maxOf(sheet.lastRowNum, maxMergedRow)
        val totalRowsToRender = actualDataEnd + 1
        val globalMaxCols = calculateGlobalMaxCols(sheet, actualDataEnd, imageMap, mergedRegions)

        return SheetInfo(imageMap, mergedRegions, rowMergedRegions, globalMaxCols, totalRowsToRender)
    }

    fun getHtmlHeader(sheet: Sheet, info: SheetInfo): String {
        val html = StringBuilder()
        html.append("<!DOCTYPE html><html><head>")
        html.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=5.0, user-scalable=yes\">")
        html.append("<style>")
        html.append(
            """
            body { font-family: sans-serif; margin: 0; padding: 10px; background-color: #f1f3f4; }
            .excel-container { display: flex; flex-direction: column; width: max-content; min-width: 100%; background-color: white; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
            .standalone-row { width: 100%; padding: 12px 15px; box-sizing: border-box; font-size: 17px; font-weight: bold; white-space: pre-wrap; word-break: normal; border-bottom: 1px solid #d1d1d1; color: #111; }
            table { border-collapse: collapse; table-layout: fixed; width: max-content; border: 1px solid #888; }
            td, th { border: 1px solid #ccc; padding: 6px 10px; font-size: 15px; white-space: pre-wrap; word-break: normal; overflow-wrap: break-word; vertical-align: middle; min-height: 24px; }
            .col-header { background-color: #e8eaed; text-align: center; font-size: 12px; font-weight: bold; color: #5f6368; position: sticky; top: 0; z-index: 20; border-bottom: 2px solid #999; height: 28px; }
            .excel-img { max-width: 100%; height: auto; display: block; margin: 5px auto; }
            a { color: #1a73e8; text-decoration: none; border-bottom: 1px solid #1a73e8; }
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
                    if (scrollTop + clientHeight >= scrollHeight - 1000) {
                        isLoading = true;
                        Android.loadMoreRows();
                    }
                }
                window.onscroll = checkScroll;
                function appendRows(html) {
                    if (!html || html.trim() === "") { setHasMore(false); return; }
                    var container = document.querySelector('.excel-container');
                    if (container) { 
                        var lastTableTbody = container.querySelector('table:last-of-type tbody');
                        if (html.startsWith('<tr') && lastTableTbody) {
                            lastTableTbody.insertAdjacentHTML('beforeend', html);
                        } else {
                            container.insertAdjacentHTML('beforeend', html);
                        }
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
        html.append("</head><body><div class=\"excel-container\">")
        return html.toString()
    }

    fun renderRowRange(sheet: Sheet, startRow: Int, endRow: Int, info: SheetInfo): String {
        val html = StringBuilder()
        val end = minOf(endRow, info.totalRowsToRender)
        var isInsideTable = false

        for (rowIndex in startRow until end) {
            val row = sheet.getRow(rowIndex)
            val rowRegions = info.rowMergedRegions[rowIndex] ?: emptyList()

            // Một hàng được coi là độc lập nếu có 1 ô gộp chiếm trên 70% chiều ngang
            val standalone = rowRegions.find { it.firstRow == rowIndex && it.lastRow == rowIndex && (it.lastColumn - it.firstColumn + 1) >= (info.globalMaxCols * 0.7) }

            if (standalone != null) {
                if (isInsideTable) { html.append("</tbody></table>"); isInsideTable = false }
                val cell = row?.getCell(standalone.firstColumn)
                html.append("<div class=\"standalone-row\"${getInlineStyle(cell)}>${renderCellContent(cell, rowIndex, standalone.firstColumn, info.imageMap)}</div>")
            } else {
                if (!isInsideTable) {
                    html.append("<table><colgroup>")
                    for (c in 0 until info.globalMaxCols) {
                        val widthPx = (sheet.getColumnWidth(c) / 30) + 50
                        html.append("<col style=\"width: ${widthPx}px;\">")
                    }
                    html.append("</colgroup><tbody>")
                    // Thêm Header A, B, C cho bảng dữ liệu đầu tiên nếu bắt đầu từ dòng 0
                    if (rowIndex == 0) {
                         html.append("<tr>")
                         for (c in 0 until info.globalMaxCols) {
                             html.append("<th class=\"col-header\">${getColumnName(c)}</th>")
                         }
                         html.append("</tr>")
                    }
                    isInsideTable = true
                }

                html.append("<tr>")
                var colIndex = 0
                while (colIndex < info.globalMaxCols) {
                    val region = rowRegions.find { it.isInRange(rowIndex, colIndex) }
                    if (region != null) {
                        if (rowIndex == region.firstRow && colIndex == region.firstColumn) {
                            val rs = region.lastRow - region.firstRow + 1
                            val cs = region.lastColumn - region.firstColumn + 1
                            val cell = row?.getCell(colIndex)
                            html.append("<td rowspan=\"$rs\" colspan=\"$cs\"${getInlineStyle(cell)}>${renderCellContent(cell, rowIndex, colIndex, info.imageMap)}</td>")
                            colIndex += cs
                        } else {
                            colIndex++
                        }
                    } else {
                        val cell = row?.getCell(colIndex)
                        html.append("<td${getInlineStyle(cell)}>${renderCellContent(cell, rowIndex, colIndex, info.imageMap)}</td>")
                        colIndex++
                    }
                }
                html.append("</tr>")
            }
        }
        if (isInsideTable) html.append("</tbody></table>")
        return html.toString()
    }

    fun getHtmlFooter(): String {
        return "</div></body></html>"
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

    private fun calculateGlobalMaxCols(sheet: Sheet, lastRow: Int, imageMap: Map<String, List<PictureData>>, regions: List<CellRangeAddress>): Int {
        var max = MIN_DISPLAY_COLS
        // Tối ưu: Chỉ quét 100 dòng đầu để tìm số cột (thay vì quét toàn bộ sheet khổng lồ)
        for (i in 0..minOf(lastRow, 100)) {
            val row = sheet.getRow(i) ?: continue
            if (row.lastCellNum > max) max = row.lastCellNum.toInt()
        }
        // Vẫn kiểm tra metadata của vùng gộp (rất nhanh)
        regions.forEach { if (it.lastColumn + 1 > max) max = it.lastColumn + 1 }
        imageMap.keys.forEach {
            val parts = it.split("_")
            if (parts.size >= 2) {
                val c = parts[1].toInt() + 1
                if (c > max) max = c
            }
        }
        return max
    }

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

    private fun renderCellContent(cell: Cell?, rowIndex: Int, colIndex: Int, imageMap: Map<String, List<PictureData>>): String {
        val sb = StringBuilder()
        val textValue = getCellValueAsString(cell)
        if (textValue.isNotEmpty()) sb.append(textValue)
        val key = "${rowIndex}_${colIndex}"
        imageMap[key]?.forEach { picData ->
            val base64 = Base64.getEncoder().encodeToString(picData.data)
            val mimeType = picData.mimeType ?: "image/png"
            if (sb.isNotEmpty()) sb.append("<br>")
            sb.append("<img class=\"excel-img\" src=\"data:$mimeType;base64,$base64\" />")
        }
        return sb.toString()
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
            CellType.NUMERIC -> if (DateUtil.isCellDateFormatted(cell)) cell.dateCellValue.toString() else {
                val num = cell.numericCellValue
                if (num == num.toLong().toDouble()) num.toLong().toString() else num.toString()
            }
            CellType.BOOLEAN -> cell.booleanCellValue.toString()
            CellType.FORMULA -> try {
                val num = cell.numericCellValue
                if (num == num.toLong().toDouble()) num.toLong().toString() else num.toString()
            } catch (e: Exception) {
                try { cell.stringCellValue ?: "" } catch (ex: Exception) { "" }
            }
            else -> ""
        }
        return linkify(escapeHtml(value)).replace("\n", "<br>")
    }

    private fun linkify(text: String): String {
        val urlPattern = Regex("(https?://[^\\s<]+)")
        return urlPattern.replace(text) { result ->
            val url = result.value
            "<a href=\"$url\">$url</a>"
        }
    }

    private fun escapeHtml(s: String): String {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;")
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
}
