package com.example.testBaseApp.ui

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.DateUtil
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.ss.util.CellRangeAddress
import java.io.InputStream

object ExcelHtmlConverter {
    fun convertToHtml(inputStream: InputStream): String {
        val workbook = WorkbookFactory.create(inputStream)
        val sheet = workbook.getSheetAt(0)

        val html = StringBuilder()

        html.append("<!DOCTYPE html><html><head>")
        html.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
        html.append("<style>")
        html.append("""
    body { 
        font-family: sans-serif; 
        margin: 8px; 
        background-color: #ffffff; 
    }
    table { 
        border-collapse: collapse; 
        width: max-content; /* Để bảng tự do dãn ngang, cho phép cuộn ngang nếu file quá to */
        min-width: 100%;
        table-layout: auto; /* Tự động tính kích thước cột theo nội dung */
    }
    td, th { 
        border: 1.5px solid #d0d0d0; 
        padding: 8px 12px; 
        font-size: 14px; 
        text-align: left; 
        
        width: auto;                  /* Không fix cứng width */
        white-space: normal;          /* Bật ngắt dòng */
        word-break: break-word;       /* Bẻ từ nếu dính liền */
        
        max-width: 40vw; 
    }
""".trimIndent())
        html.append("</style></head><body><table>")

        // 3. Lấy danh sách các vùng bị Merge (Gộp ô)
        val mergedRegions: List<CellRangeAddress> = sheet.mergedRegions

        // 4. Duyệt qua từng dòng (Row)
        for (rowIndex in 0..sheet.lastRowNum) {
            val row = sheet.getRow(rowIndex) ?: continue
            html.append("<tr>")

            val maxCols = row.lastCellNum.toInt()
            for (colIndex in 0 until maxCols) {
                // Kiểm tra ô hiện tại có thuộc vùng Merge nào không
                val mergedRegion = mergedRegions.find {
                    it.isInRange(rowIndex, colIndex)
                }

                if (mergedRegion != null) {
                    val isTopLeft = (rowIndex == mergedRegion.firstRow && colIndex == mergedRegion.firstColumn)

                    if (isTopLeft) {
                        // Tính số cột và số dòng cần gộp
                        val rowSpan = mergedRegion.lastRow - mergedRegion.firstRow + 1
                        val colSpan = mergedRegion.lastColumn - mergedRegion.firstColumn + 1
                        val cell = row.getCell(colIndex)

                        html.append("<td")
                        if (rowSpan > 1) html.append(" rowspan=\"$rowSpan\"")
                        if (colSpan > 1) html.append(" colspan=\"$colSpan\"")
                        html.append(getInlineStyle(cell))
                        html.append(">")
                        html.append(getCellValueAsString(cell))
                        html.append("</td>")
                    }
                    // Nếu là ô nằm trong vùng merge nhưng KHÔNG PHẢI ô Top-Left -> Bỏ qua
                } else {
                    // Ô bình thường
                    val cell = row.getCell(colIndex)
                    html.append("<td${getInlineStyle(cell)}>")
                    html.append(getCellValueAsString(cell))
                    html.append("</td>")
                }
            }
            html.append("</tr>")
        }

        html.append("</table></body></html>")
        workbook.close()
        return html.toString()
    }

    // Đọc Inline Style đơn giản (Căn lề Text)
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

    // Parse giá trị trong ô thành String chuẩn
    private fun getCellValueAsString(cell: Cell?): String {
        if (cell == null) return ""
        return when (cell.cellType) {
            CellType.STRING -> cell.stringCellValue.replace("\n", "<br>")
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
                    cell.stringCellValue ?: ""
                }
            }
            else -> ""
        }
    }
}