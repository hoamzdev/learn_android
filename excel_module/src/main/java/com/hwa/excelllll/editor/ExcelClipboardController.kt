package com.hwa.excelllll.editor

import android.content.Context
import com.hwa.excelllll.SODoc

/**
 * Clipboard bridge around SODoc.
 */
class ExcelClipboardController(
    private val document: SODoc
) {
    fun hasData(): Boolean = document.clipboardHasData()

    fun copy() {
        if (document.getSelectionCanBeCopied()) {
            document.selectionCopyToClip()
        }
    }

    fun cut() {
        if (document.getSelectionCanBeCopied() &&
            document.getSelectionCanBeDeleted()
        ) {
            document.selectionCutToClip()
        }
    }

    fun paste(context: Context, mode: Int = 0) {
        if (!document.getSelectionCanBePasteTarget()) return
        document.selectionPaste(mode)
    }

    fun pasteNative(mode: Int = 0) {
        if (document.getSelectionCanBePasteTarget()) {
            document.selectionPaste(mode)
        }
    }

    fun text(): String? = document.getClipboardAsText()
}
