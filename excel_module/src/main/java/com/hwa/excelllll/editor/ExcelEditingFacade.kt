package com.hwa.excelllll.editor

import android.content.Context
import com.hwa.excelllll.core.SODoc
import com.hwa.excelllll.core.SODocSaveListener

/**
 * One small API for the Excel screen.
 *
 * UI code should normally use this facade instead of calling SODoc directly.
 */
class ExcelEditingFacade(
    private val document: SODoc,
    context: Context? = null
) {
    val cells = ExcelCellEditor(document)
    val selection = ExcelSelectionModel(document)
    val formatting = ExcelFormattingController(document)
    val clipboard = ExcelClipboardController(document)

    private val appContext = context

    fun replace(text: String) {
        cells.replaceSelection(text)
    }

    fun delete() {
        cells.clearSelection()
    }

    fun copy() = clipboard.copy()
    fun cut() = clipboard.cut()

    fun paste() {
        appContext?.let { clipboard.paste(it) }
            ?: clipboard.pasteNative()
    }

    fun save(path: String, listener: SODocSaveListener) {
        document.save(path, listener)
    }

    fun isModified(): Boolean = document.getHasBeenModified()
}
