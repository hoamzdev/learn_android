package com.hwa.excelllll.excel

import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.RectF
import kotlin.math.roundToInt

/**
 * Replaces the decompiler-generated arithmetic helpers with explicit
 * coordinate conversions.
 *
 * Artifex page coordinates are converted using the same factor used by
 * DocPageView: scale * zoom.
 */
class ExcelCoordinateMapper(
    private var scale: Float = 1f,
    private var zoom: Double = 1.0
) {
    fun setScale(value: Float) {
        scale = value
    }

    fun setZoom(value: Double) {
        zoom = value
    }

    fun factor(): Double = scale.toDouble() * zoom

    fun pageToView(x: Int): Int =
        (x * factor()).roundToInt()

    fun pageToView(x: Float): Float =
        (x * factor()).toFloat()

    fun pageToView(point: Point): Point =
        Point(pageToView(point.x), pageToView(point.y))

    fun pageToView(point: PointF): PointF =
        PointF(pageToView(point.x), pageToView(point.y))

    fun pageToView(rect: Rect): Rect =
        Rect(
            pageToView(rect.left),
            pageToView(rect.top),
            pageToView(rect.right),
            pageToView(rect.bottom)
        )

    fun pageToView(rect: RectF): RectF =
        RectF(
            pageToView(rect.left),
            pageToView(rect.top),
            pageToView(rect.right),
            pageToView(rect.bottom)
        )

    fun viewToPage(x: Int): Int =
        (x / factor()).roundToInt()

    fun viewToPage(x: Float): Float =
        (x / factor()).toFloat()

    fun viewToPage(point: Point): Point =
        Point(viewToPage(point.x), viewToPage(point.y))

    fun viewToPage(point: PointF): PointF =
        PointF(viewToPage(point.x), viewToPage(point.y))

    fun viewToPage(rect: Rect): Rect =
        Rect(
            viewToPage(rect.left),
            viewToPage(rect.top),
            viewToPage(rect.right),
            viewToPage(rect.bottom)
        )

    fun viewToPage(rect: RectF): RectF =
        RectF(
            viewToPage(rect.left),
            viewToPage(rect.top),
            viewToPage(rect.right),
            viewToPage(rect.bottom)
        )
}
