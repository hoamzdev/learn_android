package com.hwa.excelllll.editor

import com.hwa.excelllll.core.SODoc

class ExcelRowColumnController(
    private val document: SODoc
) {
    fun insertRowAbove() = document.addRowsAbove()
    fun insertRowBelow() = document.addRowsBelow()
    fun deleteSelectedRows() = document.deleteRows()

    fun insertColumnLeft() = document.addColumnsLeft()
    fun insertColumnRight() = document.addColumnsRight()
    fun deleteSelectedColumns() = document.deleteColumns()

    fun selectedColumnWidth(): Float =
        document.getSelectedColumnWidth()

    fun selectedRowHeight(): Float =
        document.getSelectedRowHeight()

    fun setColumnWidth(width: Float) =
        document.setSelectedColumnWidth(width)

    fun setRowHeight(height: Float) =
        document.setSelectedRowHeight(height)
}
