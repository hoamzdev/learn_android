package com.hwa.excelllll.editor

import android.graphics.PointF
import com.hwa.excelllll.core.SOSelectionLimits
import com.hwa.excelllll.core.SOSelectionTableRange
import com.hwa.excelllll.core.SODoc

/**
 * Read/write access to the native selection.
 */
class ExcelSelectionModel(
    private val document: SODoc
) {
    fun clear() = document.clearSelection()

    fun text(): String? = document.getSelectionAsText()

    fun tableRange(): SOSelectionTableRange? = document.selectionTableRange()

    fun limits(): SOSelectionLimits? = null

    fun naturalSize(): PointF = document.selectionNaturalSize()

    fun canCopy() = document.getSelectionCanBeCopied()
    fun canDelete() = document.getSelectionCanBeDeleted()
    fun canPaste() = document.getSelectionCanBePasteTarget()
    fun canEditText() = document.getSelectionCanHaveTextAltered()
    fun canResize() = document.getSelectionCanBeResized()
    fun canRotate() = document.getSelectionCanBeRotated()

    fun setLimits(left: Float, top: Float, right: Float, bottom: Float) {
        throw UnsupportedOperationException("Selection bounds must be set through SOPage")
    }

    fun setText(text: String, mode: Int = 0, flag: Boolean = true) {
        if (canEditText()) {
            document.setSelectionText(text, mode, flag)
        }
    }
}
