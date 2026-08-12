package com.hwa.excelllll.ui.editor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ViewGroup
import android.view.ViewConfiguration
import android.widget.FrameLayout
import com.hwa.excelllll.core.SOBitmap
import com.hwa.excelllll.core.SODoc
import com.hwa.excelllll.editor.ExcelBitmapProvider
import com.hwa.excelllll.editor.ExcelRenderController
import com.hwa.excelllll.editor.ExcelRenderRequest

/**
 * Host for one Excel page.
 *
 * Keeps rendering concerns outside Activity/Fragment.
 */
@SuppressLint("ViewConstructor")
class ExcelPageHost(
    context: Context,
    document: SODoc,
    bitmapProvider: ExcelBitmapProvider
) : FrameLayout(context) {

    private val surface = ExcelRenderSurface(context)
    private val renderer = ExcelRenderController(document, bitmapProvider)
    private var currentPage = 0
    private var currentScale = 1.0
    private var contentSize = Point()
    private var offsetX = 0
    private var offsetY = 0
    private var lastX = 0f
    private var lastY = 0f
    private val touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    private var dragging = false
    private var viewportRenderScheduled = false
    private var renderedOffsetX = 0
    private var renderedOffsetY = 0
    private var renderedScale = 1.0

    private val scaleDetector = ScaleGestureDetector(
        context,
        object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                val oldScale = currentScale
                val newScale = (oldScale * detector.scaleFactor).coerceIn(MIN_SCALE, MAX_SCALE)
                if (newScale == oldScale) return true

                val ratio = newScale / oldScale
                offsetX = ((offsetX + detector.focusX) * ratio - detector.focusX).toInt()
                    .coerceAtLeast(0)
                offsetY = ((offsetY + detector.focusY) * ratio - detector.focusY).toInt()
                    .coerceAtLeast(0)
                currentScale = newScale
                updatePreviewTransform()
                return true
            }

            override fun onScaleEnd(detector: ScaleGestureDetector) {
                renderViewport()
            }
        }
    )

    init {
        addView(
            surface,
            LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        isClickable = true
    }

    fun render(
        pageIndex: Int,
        width: Int,
        height: Int,
        scale: Double = DEFAULT_SCALE,
        onComplete: (Int) -> Unit = {}
    ) {
        currentPage = pageIndex
        currentScale = scale
        renderViewport(onComplete)
    }

    private fun renderViewport(onComplete: (Int) -> Unit = {}) {
        if (width <= 0 || height <= 0) return
        val requestedOffsetX = offsetX
        val requestedOffsetY = offsetY
        val requestedScale = currentScale
        renderer.render(
            ExcelRenderRequest(
                pageIndex = currentPage,
                width = width,
                height = height,
                scale = currentScale,
                offsetX = offsetX,
                offsetY = offsetY
            ),
            onBitmapReady = { bitmap ->
                renderedOffsetX = requestedOffsetX
                renderedOffsetY = requestedOffsetY
                renderedScale = requestedScale
                show(bitmap)
                updatePreviewTransform()
            },
            onPageSize = { size ->
                contentSize = size
                offsetX = offsetX.coerceIn(0, (size.x - width).coerceAtLeast(0))
                offsetY = offsetY.coerceIn(0, (size.y - height).coerceAtLeast(0))
            },
            onComplete = onComplete
        )
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        scaleDetector.onTouchEvent(event)
        return super.dispatchTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
                lastY = event.y
                dragging = false
            }
            MotionEvent.ACTION_POINTER_DOWN -> return true
            MotionEvent.ACTION_MOVE -> {
                if (scaleDetector.isInProgress ||
                    kotlin.math.abs(event.x - lastX) > touchSlop ||
                    kotlin.math.abs(event.y - lastY) > touchSlop
                ) return true
            }
        }
        return super.onInterceptTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
                lastY = event.y
                dragging = false
                parent?.requestDisallowInterceptTouchEvent(true)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (scaleDetector.isInProgress) return true
                val dx = lastX - event.x
                val dy = lastY - event.y
                if (!dragging && (kotlin.math.abs(dx) > touchSlop || kotlin.math.abs(dy) > touchSlop)) {
                    dragging = true
                }
                if (dragging) {
                    offsetX = (offsetX + dx.toInt()).coerceIn(
                        0, (contentSize.x - width).coerceAtLeast(0)
                    )
                    offsetY = (offsetY + dy.toInt()).coerceIn(
                        0, (contentSize.y - height).coerceAtLeast(0)
                    )
                    lastX = event.x
                    lastY = event.y
                    updatePreviewTransform()
                    scheduleViewportRender()
                }
                return true
            }
            MotionEvent.ACTION_POINTER_DOWN, MotionEvent.ACTION_POINTER_UP -> return true
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                removeCallbacks(viewportRenderAction)
                viewportRenderScheduled = false
                if (dragging && !scaleDetector.isInProgress) renderViewport()
                dragging = false
                parent?.requestDisallowInterceptTouchEvent(false)
                performClick()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private val viewportRenderAction = Runnable {
        viewportRenderScheduled = false
        renderViewport()
    }

    private fun scheduleViewportRender() {
        // Throttle viewport renders while the finger is moving. Resetting the
        // callback on every ACTION_MOVE behaves like a debounce: during a
        // continuous drag it never runs, so fresh content only appears after
        // ACTION_UP. Keeping the first scheduled frame lets the viewport load
        // continuously without starting a native render for every move event.
        if (viewportRenderScheduled) return
        viewportRenderScheduled = true
        postDelayed(viewportRenderAction, VIEWPORT_RENDER_DELAY_MS)
    }

    private fun updatePreviewTransform() {
        val previewScale = (currentScale / renderedScale).toFloat()
        surface.pivotX = 0f
        surface.pivotY = 0f
        surface.scaleX = previewScale
        surface.scaleY = previewScale
        surface.translationX = (renderedOffsetX - offsetX).toFloat()
        surface.translationY = (renderedOffsetY - offsetY).toFloat()
    }

    private fun show(value: SOBitmap) {
        val bitmap = value.bitmap()
        if (!bitmap.isRecycled) {
            surface.setBitmap(bitmap)
        }
    }

    fun cancelRender() {
        renderer.cancel()
    }

    fun close() {
        removeCallbacks(viewportRenderAction)
        renderer.close()
        surface.clearBitmap()
    }

    companion object {
        private const val VIEWPORT_RENDER_DELAY_MS = 100L
        private const val DEFAULT_SCALE = 1.2
        private const val MIN_SCALE = 0.5
        private const val MAX_SCALE = 8.0
    }
}
