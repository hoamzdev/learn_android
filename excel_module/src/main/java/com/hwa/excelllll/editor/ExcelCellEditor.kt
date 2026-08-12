package com.hwa.excelllll.editor

import com.hwa.excelllll.core.SODoc

/**
 * Operations that change the currently selected Excel cell/range.
 * The actual spreadsheet mutation is still performed by the Artifex native engine.
 */
class ExcelCellEditor(
    private val document: SODoc
) {
    fun replaceSelection(text: String, mode: Int = 0, allowFormatting: Boolean = true) {
        if (document.getSelectionCanHaveTextAltered()) {
            document.setSelectionText(text, mode, allowFormatting)
        }
    }

    fun clearSelection() {
        if (document.getSelectionCanBeDeleted()) {
            document.selectionDelete()
        }
    }

    fun deleteCharacter() {
        document.deleteChar()
    }

    fun insertRowAbove() = document.addRowsAbove()
    fun insertRowBelow() = document.addRowsBelow()
    fun insertColumnLeft() = document.addColumnsLeft()
    fun insertColumnRight() = document.addColumnsRight()

    fun deleteRows() = document.deleteRows()
    fun deleteColumns() = document.deleteColumns()

    fun mergeCells(enabled: Boolean) {
        document.setTableCellsMerged(enabled)
    }
}
