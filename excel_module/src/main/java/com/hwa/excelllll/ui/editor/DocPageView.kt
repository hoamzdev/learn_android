package com.hwa.excelllll.ui.editor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.RectF
import android.view.View
import android.view.ViewGroup
import com.hwa.excelllll.core.InterfaceC1335o
import com.hwa.excelllll.core.SOBitmap
import com.hwa.excelllll.core.SODoc
import com.hwa.excelllll.core.SOPage
import com.hwa.excelllll.core.SOPageListener
import com.hwa.excelllll.core.SORender
import com.hwa.excelllll.core.SOSelectionLimits
import kotlin.math.roundToInt

/**
 * Clean page view extracted from the original Artifex DocPageView.
 *
 * Responsibilities:
 * - own one SOPage
 * - calculate page/view coordinates
 * - request native rendering
 * - display the latest rendered bitmap
 * - forward selection/edit gestures to SOPage/SODoc
 */
@SuppressLint("ViewConstructor")
open class DocPageView(
    context: Context,
    private val document: SODoc
) : View(context), SOPageListener {

    interface ExternalLinkListener {
        fun handleExternalLink(pageNumber: Int, bounds: RectF)
    }

    private val bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)

    private var page: SOPage? = null
    private var pageIndex = -1
    private var renderJob: SORender? = null
    private var renderBitmap: SOBitmap? = null
    private var renderSourceRect = Rect()
    private var finished = false
    private var valid = true
    private var current = false

    var zoom: Double = 1.0
        private set

    var scale: Float = 1f
        private set

    var pageSize: Point = Point()
        private set

    var renderOrigin: PointF = PointF()
        private set

    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    fun setup(index: Int, width: Int, height: Int) {
        if (finished || !valid) return

        changePage(index)

        val currentPage = page ?: return
        val fit = currentPage.zoomToFitRect(width, height)
        zoom = maxOf(fit.x.toDouble(), fit.y.toDouble())
        pageSize = currentPage.sizeAtZoom(zoom)

        requestLayout()
    }

    fun changePage(index: Int) {
        if (finished || !valid || index == pageIndex && page != null) return

        dropPage()

        pageIndex = index
        page = document.getPage(index, this)
    }

    fun render(source: SOBitmap, callback: InterfaceC1335o) {
        if (finished || !valid || page == null) {
            callback.onRenderComplete(0)
            return
        }

        stopRender()

        val visible = Rect()
        if (!getLocalVisibleRect(visible)) {
            callback.onRenderComplete(0)
            return
        }

        val global = Rect()
        if (!getGlobalVisibleRect(global)) {
            callback.onRenderComplete(0)
            return
        }

        val expanded = Rect(visible)
        val margin = 64
        expanded.inset(-margin, -margin)

        val pageBounds = Rect(0, 0, source.width(), source.height())
        expanded.intersect(pageBounds)

        if (expanded.isEmpty) {
            callback.onRenderComplete(0)
            return
        }

        val target = source.crop(
            expanded.left,
            expanded.top,
            expanded.right,
            expanded.bottom
        )

        renderSourceRect = expanded

        renderJob = page?.render(
            layer = -2,
            zoom = zoom * scale,
            origin = renderOrigin,
            target = target,
            listener = InterfaceC1335o { status ->
                if (finished) return@InterfaceC1335o

                if (status == 0) {
                    renderBitmap = target
                    invalidate()
                } else {
                    renderBitmap = null
                }

                callback.onRenderComplete(status)
            },
            dispatchOnMainThread = true
        )
    }

    fun stopRender() {
        renderJob?.let {
            runCatching { it.abort() }
            runCatching { it.destroy() }
        }
        renderJob = null
    }

    fun dropPage() {
        stopRender()
        page?.let { runCatching { it.discard() } }
        page = null
    }

    fun finish() {
        if (finished) return
        finished = true
        dropPage()
        renderBitmap = null
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (finished || !valid) return

        val rendered = renderBitmap ?: return
        val bitmap = rendered.bitmap()

        if (bitmap.isRecycled) return

        val source = rendered.bounds()
        val destination = Rect(
            0,
            0,
            width,
            height
        )

        canvas.drawBitmap(bitmap, source, destination, bitmapPaint)
    }

    fun pageToView(value: Int): Int =
        (value * factor()).roundToInt()

    fun pageToView(point: Point): Point =
        Point(pageToView(point.x), pageToView(point.y))

    fun pageToView(point: PointF): PointF {
        val f = factor()
        return PointF(point.x * f, point.y * f)
    }

    fun pageToView(rect: Rect, out: Rect) {
        val f = factor()
        out.set(
            (rect.left * f).roundToInt(),
            (rect.top * f).roundToInt(),
            (rect.right * f).roundToInt(),
            (rect.bottom * f).roundToInt()
        )
    }

    fun pageToView(rect: RectF): Rect {
        val f = factor()
        return Rect(
            (rect.left * f).roundToInt(),
            (rect.top * f).roundToInt(),
            (rect.right * f).roundToInt(),
            (rect.bottom * f).roundToInt()
        )
    }

    fun viewToPage(value: Int): Int =
        (value / factor()).roundToInt()

    fun viewToPage(x: Int, y: Int): Point =
        Point(viewToPage(x), viewToPage(y))

    fun screenToPage(x: Int, y: Int): Point {
        val location = IntArray(2)
        getLocationOnScreen(location)
        return viewToPage(x - location[0], y - location[1])
    }

    fun screenToPage(point: Point): Point =
        screenToPage(point.x, point.y)

    fun pageToScreen(point: Point): Point {
        val location = IntArray(2)
        getLocationOnScreen(location)
        val local = pageToView(point)
        return Point(local.x + location[0], local.y + location[1])
    }

    fun factor(): Float = (zoom * scale).toFloat()

    fun selectionLimits(): SOSelectionLimits? =
        if (finished) null else page?.selectionLimits()

    fun selectTopLeft() {
        page?.select(2, 0.0, 0.0)
    }

    fun setSelectionStart(screenPoint: Point) {
        page?.select(0, screenToPage(screenPoint).let { PointF(it.x.toFloat(), it.y.toFloat()) })
    }

    fun setSelectionEnd(screenPoint: Point) {
        page?.select(1, screenToPage(screenPoint).let { PointF(it.x.toFloat(), it.y.toFloat()) })
    }

    fun onSingleTap(
        screenX: Int,
        screenY: Int,
        selectCell: Boolean,
        externalLinkListener: ExternalLinkListener? = null
    ): Boolean {
        val p = screenToPage(screenX, screenY)
        val link = page?.objectAtPoint(p.x.toFloat(), p.y.toFloat())

        if (link != null) {
            if (link.pageNum >= 0) {
                externalLinkListener?.handleExternalLink(
                    link.pageNum,
                    RectF(link.bbox ?: Rect())
                )
                return true
            }
            if (!link.url.isNullOrBlank()) {
                return true
            }
        }

        if (selectCell) {
            document.clearSelection()
            page?.select(3, p.x.toDouble(), p.y.toDouble())
        }

        return false
    }

    fun onDoubleTap(screenX: Int, screenY: Int) {
        val p = screenToPage(screenX, screenY)
        page?.select(2, p.x.toDouble(), p.y.toDouble())
    }

    fun setNewScale(value: Float) {
        scale = value
        requestLayout()
        invalidate()
    }

    fun resize(width: Int, height: Int) {
        val currentPage = page ?: return
        val fit = currentPage.zoomToFitRect(width, height)
        zoom = maxOf(fit.x.toDouble(), fit.y.toDouble())
        pageSize = currentPage.sizeAtZoom(zoom)
        requestLayout()
    }

    fun setOrigin(x: Float, y: Float) {
        renderOrigin = PointF(x, y)
    }

    fun setOriginFromRect(rect: Rect) {
        renderOrigin = PointF(-rect.left.toFloat(), -rect.top.toFloat())
    }

    fun setCurrent(value: Boolean) {
        if (current != value) {
            current = value
            invalidate()
        }
    }

    fun isCurrent(): Boolean = current
    fun isFinished(): Boolean = finished
    fun isValid(): Boolean = valid
    fun setValid(value: Boolean) {
        if (valid == value) return
        valid = value
        if (!value) {
            stopRender()
            renderBitmap = null
        }
        invalidate()
    }

    fun getPage(): SOPage? = page
    fun getPageNumber(): Int = pageIndex
    fun getDoc(): SODoc = document
    fun getSize(): Point = Point(pageSize)

    override fun update(rect: RectF) {
        postInvalidate()
    }

    override fun onDetachedFromWindow() {
        stopRender()
        super.onDetachedFromWindow()
    }
}
