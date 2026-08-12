package com.hwa.excelllll.ui.editor

import android.graphics.PointF
import com.hwa.excelllll.editor.ExcelEditor

data class ExcelEditorUiState(
    val isOpen: Boolean = false,
    val modified: Boolean = false,
    val selectionText: String? = null,
    val fontName: String? = null,
    val fontSize: Double = 0.0,
    val bold: Boolean = false,
    val italic: Boolean = false,
    val underline: Boolean = false,
    val alignment: Int = 0
)

class ExcelEditorViewModel {

    private var editor: ExcelEditor? = null

    fun attach(value: ExcelEditor) {
        editor = value
    }

    fun detach() {
        editor = null
    }

    fun replaceSelection(text: String) {
        editor?.replaceSelection(text)
    }

    fun setBold(value: Boolean) {
        editor?.setBold(value)
    }

    fun setItalic(value: Boolean) {
        editor?.setItalic(value)
    }

    fun setUnderline(value: Boolean) {
        editor?.setUnderline(value)
    }

    fun setFontSize(value: Double) {
        editor?.setFontSize(value)
    }

    fun setFontName(value: String) {
        editor?.setFontName(value)
    }

    fun copy() = editor?.copy()

    fun cut() = editor?.cut()

    fun paste() = editor?.paste()
}
