package com.hwa.excelllll.editor

import com.hwa.excelllll.SODoc

/** Small UI-facing facade over the native editing controllers. */
class ExcelEditor(document: SODoc) {
    private val cells = ExcelCellEditor(document)
    private val formatting = ExcelFormattingController(document)
    private val clipboard = ExcelClipboardController(document)

    fun replaceSelection(text: String) = cells.replaceSelection(text)
    fun setBold(value: Boolean) = formatting.bold(value)
    fun setItalic(value: Boolean) = formatting.italic(value)
    fun setUnderline(value: Boolean) = formatting.underline(value)
    fun setFontSize(value: Double) = formatting.fontSize(value)
    fun setFontName(value: String) = formatting.fontName(value)
    fun copy() = clipboard.copy()
    fun cut() = clipboard.cut()
    fun paste() = clipboard.pasteNative()
}
