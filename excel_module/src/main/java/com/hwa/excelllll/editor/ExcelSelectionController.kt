package com.hwa.excelllll.editor

import android.graphics.PointF
import com.hwa.excelllll.SOSelectionTableRange
import com.hwa.excelllll.SODoc

class ExcelSelectionController(
    private val document: SODoc
) {
    fun clear() = document.clearSelection()

    fun copy() {
        if (document.getSelectionCanBeCopied()) {
            document.selectionCopyToClip()
        }
    }

    fun cut() {
        if (document.getSelectionCanBeCopied() && document.getSelectionCanBeDeleted()) {
            document.selectionCutToClip()
        }
    }

    fun delete() {
        if (document.getSelectionCanBeDeleted()) {
            document.selectionDelete()
        } else {
            document.deleteChar()
        }
    }

    fun text(): String? = document.getSelectionAsText()

    fun tableRange(): SOSelectionTableRange? = document.selectionTableRange()

    fun naturalSize(): PointF = document.selectionNaturalSize()

    fun canCopy(): Boolean = document.getSelectionCanBeCopied()

    fun canDelete(): Boolean = document.getSelectionCanBeDeleted()

    fun canPaste(): Boolean = document.getSelectionCanBePasteTarget()

    fun canEditText(): Boolean = document.getSelectionCanHaveTextAltered()

    fun canResize(): Boolean = document.getSelectionCanBeResized()

    fun canRotate(): Boolean = document.getSelectionCanBeRotated()

    fun setSelectionText(text: String, mode: Int = 0, flag: Boolean = true) {
        document.setSelectionText(text, mode, flag)
    }

    fun setLimits(left: Float, top: Float, right: Float, bottom: Float) {
        throw UnsupportedOperationException("Selection bounds must be set through SOPage")
    }
}
