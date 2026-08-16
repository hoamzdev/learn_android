package com.hwa.excelllll.ui.editor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.view.ViewGroup
import com.hwa.excelllll.SOBitmap
import com.hwa.excelllll.SODoc

/**
 * Minimal page container for an Excel document.
 *
 * It intentionally does not contain the old NUIDocView/DocView machinery.
 * That machinery can be added later only if a feature actually needs it.
 */
@SuppressLint("ViewConstructor")
class ExcelPageContainer(
    context: Context,
    private val document: SODoc
) : ViewGroup(context) {

    private val pages = mutableListOf<DocPageView>()

    fun showPage(index: Int, width: Int, height: Int): DocPageView {
        while (pages.size <= index) {
            pages += DocPageView(context, document)
        }

        removeAllViews()
        val page = pages[index]
        page.setup(index, width, height)
        addView(page)
        return page
    }

    fun renderPage(
        index: Int,
        bitmap: Bitmap,
        onComplete: (Int) -> Unit = {}
    ) {
        val page = showPage(index, width.coerceAtLeast(1), height.coerceAtLeast(1))
        page.render(SOBitmap(bitmap, Rect(0, 0, bitmap.width, bitmap.height))) {
            onComplete(it)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = MeasureSpec.getSize(widthMeasureSpec)
        val h = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(w, h)

        for (child in 0 until childCount) {
            getChildAt(child).measure(
                MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY)
            )
        }
    }

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        if (childCount == 0) return
        getChildAt(0).layout(0, 0, right - left, bottom - top)
    }

    override fun onDetachedFromWindow() {
        pages.forEach { it.finish() }
        super.onDetachedFromWindow()
    }
}
