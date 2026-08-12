package com.hwa.excelllll.ui.editor

import android.graphics.Point
import com.hwa.excelllll.core.SODoc

/**
 * Converts Android touch coordinates to native page coordinates and
 * performs native selection.
 *
 * Selection mode follows the original Artifex implementation:
 *   2 = double-tap/select
 *   3 = single-tap/caret selection
 */
class ExcelTouchSelectionController(
    private val document: SODoc
) {
    fun selectAt(
        pageView: ExcelPageView,
        x: Int,
        y: Int,
        select: Boolean = true
    ): Boolean {
        val page = pageView.getPage() ?: return false
        if (!select) return false

        val point: Point = pageView.screenToPage(x, y)
        document.clearSelection()
        page.select(3, point.x.toDouble(), point.y.toDouble())
        return true
    }

    fun selectAtPageCoordinates(
        pageView: ExcelPageView,
        x: Float,
        y: Float
    ) {
        val page = pageView.getPage() ?: return
        page.select(3, x.toDouble(), y.toDouble())
    }

    fun handleDoubleTap(
        pageView: ExcelPageView,
        x: Int,
        y: Int
    ) {
        val page = pageView.getPage() ?: return
        val point = pageView.screenToPage(x, y)
        page.select(2, point.x.toDouble(), point.y.toDouble())
    }
}
