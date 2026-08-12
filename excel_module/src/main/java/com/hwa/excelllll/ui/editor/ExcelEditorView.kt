package com.hwa.excelllll.ui.editor

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.hwa.excelllll.SODoc

/**
 * Minimal host View for the Artifex page renderer.
 *
 * Page creation/rendering remains delegated to ExcelPageView.
 * This class intentionally does not implement an XLS/XLSX parser.
 */
class ExcelEditorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var controller: ExcelEditorController? = null
    private var pageView: ExcelPageView? = null
    private var touchController: ExcelTouchSelectionController? = null

    fun bind(document: SODoc) {
        controller?.close()

        controller = ExcelEditorController(document, context.applicationContext)
        touchController = ExcelTouchSelectionController(document)

        val page = ExcelPageView(context, document)
        pageView = page

        removeAllViews()
        addView(page, LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        ))
    }

    fun getEditorController(): ExcelEditorController? = controller
    fun getPageView(): ExcelPageView? = pageView

    fun unbind() {
        controller?.close()
        controller = null
        pageView?.finish()
        pageView = null
        touchController = null
        removeAllViews()
    }

    override fun onDetachedFromWindow() {
        unbind()
        super.onDetachedFromWindow()
    }
}
