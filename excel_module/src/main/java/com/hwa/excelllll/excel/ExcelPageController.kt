package com.hwa.excelllll.excel

import android.graphics.Point
import android.graphics.PointF
import android.graphics.RectF
import com.hwa.excelllll.core.SODoc
import com.hwa.excelllll.core.SOPage
import com.hwa.excelllll.core.SOSelectionLimits

/**
 * Page/cell coordinate and selection operations used by the Excel editor.
 */
class ExcelPageController(
    private val documentProvider: () -> SODoc?
) {
    private var currentPage: SOPage? = null
    private var pageIndex: Int = -1

    fun setPage(index: Int, page: SOPage?) {
        pageIndex = index
        currentPage = page
    }

    fun pageIndex(): Int = pageIndex

    fun page(): SOPage? = currentPage

    fun selectionLimits(): SOSelectionLimits? =
        currentPage?.selectionLimits()

    fun select(type: Int, x: Double, y: Double) {
        currentPage?.select(type, x, y)
    }

    fun setSelectionBox(rect: RectF) {
        currentPage?.setSelectionBox(rect)
    }

    fun pageSize(zoom: Double): Point? =
        currentPage?.sizeAtZoom(zoom)

    fun zoomToFit(width: Int, height: Int): PointF? =
        currentPage?.zoomToFitRect(width, height)

    fun objectAtPoint(x: Float, y: Float) =
        currentPage?.objectAtPoint(x, y)

    fun clearSelection() {
        documentProvider()?.clearSelection()
    }

    fun release() {
        try {
            currentPage?.discard()
        } catch (_: Throwable) {
        }
        currentPage = null
        pageIndex = -1
    }
}
