package com.hwa.excelllll.editor

import com.hwa.excelllll.SODoc

class ExcelEditController(
    private val document: SODoc
) {
    fun replaceSelection(text: String) {
        document.setSelectionText(text, 0, true)
    }

    fun deleteSelection() {
        if (document.getSelectionCanBeDeleted()) {
            document.selectionDelete()
        }
    }

    fun deleteCharacter() {
        document.deleteChar()
    }

    fun addRowAbove() = document.addRowsAbove()

    fun addRowBelow() = document.addRowsBelow()

    fun addColumnLeft() = document.addColumnsLeft()

    fun addColumnRight() = document.addColumnsRight()

    fun deleteRows() = document.deleteRows()

    fun deleteColumns() = document.deleteColumns()

    fun mergeCells(merge: Boolean) {
        document.setTableCellsMerged(merge)
    }
}
