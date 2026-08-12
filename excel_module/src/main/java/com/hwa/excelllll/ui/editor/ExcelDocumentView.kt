package com.hwa.excelllll.ui.editor

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ScrollView
import com.hwa.excelllll.core.SOBitmap
import com.hwa.excelllll.core.SODoc

/**
 * Simple vertical document container.
 *
 * It is intentionally independent from the original NUIDocView/DocView hierarchy.
 */
@SuppressLint("ViewConstructor")
class ExcelDocumentView(
    context: Context,
    private val document: SODoc
) : ScrollView(context) {

    private val pagesContainer = android.widget.LinearLayout(context).apply {
        orientation = android.widget.LinearLayout.VERTICAL
    }

    private val pages = mutableListOf<ExcelPageView>()

    init {
        addView(
            pagesContainer,
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        )
    }

    fun setPageCount(count: Int) {
        clearPages()

        repeat(count) { index ->
            val pageView = ExcelPageView(context, document)
            pageView.bindPage(index)

            pages += pageView
            pagesContainer.addView(
                pageView,
                android.widget.LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
                ).apply {
                    bottomMargin = dp(8)
                }
            )
        }
    }

    fun renderPage(index: Int, bitmap: SOBitmap, scale: Double = 1.0) {
        pages.getOrNull(index)?.renderPage(bitmap, scale)
    }

    fun pageView(index: Int): ExcelPageView? = pages.getOrNull(index)

    fun pageCount(): Int = pages.size

    fun clearPages() {
        pages.forEach { it.releasePage() }
        pages.clear()
        pagesContainer.removeAllViews()
    }

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()
}
