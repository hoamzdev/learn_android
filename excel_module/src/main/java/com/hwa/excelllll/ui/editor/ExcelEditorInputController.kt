package com.hwa.excelllll.ui.editor

import android.view.inputmethod.InputMethodManager
import android.content.Context
import android.view.View
import android.widget.FrameLayout

/**
 * Keyboard bridge for editing the current native selection.
 */
class ExcelEditorInputController(
    private val host: ExcelEditorView
) {
    private var editorView: ExcelCellEditView? = null

    fun beginEdit() {
        val controller = host.getEditorController() ?: return
        val input = ExcelCellEditView(host.context, controller.session.doc)

        editorView = input
        host.addView(
            input,
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
        )

        input.beginEdit()
        input.post {
            val imm = host.context.getSystemService(Context.INPUT_METHOD_SERVICE)
                    as? InputMethodManager
            imm?.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun commit() {
        val input = editorView ?: return
        input.commitEdit()
        removeInput()
    }

    fun cancel() {
        editorView?.cancelEdit()
        removeInput()
    }

    private fun removeInput() {
        val input = editorView ?: return
        val imm = host.context.getSystemService(Context.INPUT_METHOD_SERVICE)
                as? InputMethodManager
        imm?.hideSoftInputFromWindow(input.windowToken, 0)
        host.removeView(input)
        editorView = null
    }
}
