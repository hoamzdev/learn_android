package com.hwa.excelllll.ui.editor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.RectF
import android.graphics.drawable.GradientDrawable
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.view.ViewConfiguration
import android.widget.FrameLayout
import android.widget.OverScroller
import com.hwa.excelllll.SOBitmap
import com.hwa.excelllll.SODoc
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
    private var offsetX = 0.0
    private var offsetY = 0.0
    private var lastX = 0f
    private var lastY = 0f
    private val touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    private var dragging = false
    private var viewportRenderScheduled = false
    private var renderedOffsetX = 0.0
    private var renderedOffsetY = 0.0
    private var renderedScale = 1.0

    private lateinit var handles: Array<View>
    private var isDraggingHandle = false

    // Thêm Scroller để hỗ trợ cuộn theo quán tính (Fling)
    private val scroller = OverScroller(context)

    /** Called after a non-dragging single tap selects a spreadsheet cell. */
    var onCellSelected: (() -> Unit)? = null

    private val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            selectCellAt(e.x, e.y)
            return true
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            if (isDraggingHandle || scaleDetector.isInProgress) return false
            
            // Bắt đầu tính quán tính
            scroller.fling(
                offsetX.toInt(), offsetY.toInt(),
                (-velocityX).toInt(), (-velocityY).toInt(),
                0, (contentSize.x - width).coerceAtLeast(0),
                0, (contentSize.y - height).coerceAtLeast(0),
                0, 0
            )
            postInvalidateOnAnimation()
            return true
        }
    })

    private val scaleDetector = ScaleGestureDetector(
        context,
        object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                val oldScale = currentScale
                val newScale = (oldScale * detector.scaleFactor).coerceIn(MIN_SCALE, MAX_SCALE)
                if (newScale == oldScale) return true

                val ratio = newScale / oldScale
                if (contentSize.x > 0 && contentSize.y > 0) {
                    contentSize.x = (contentSize.x * ratio).toInt()
                    contentSize.y = (contentSize.y * ratio).toInt()
                }

                offsetX = ((offsetX + detector.focusX) * ratio - detector.focusX)
                    .coerceIn(0.0, (contentSize.x - width).coerceAtLeast(0).toDouble())
                offsetY = ((offsetY + detector.focusY) * ratio - detector.focusY)
                    .coerceIn(0.0, (contentSize.y - height).coerceAtLeast(0).toDouble())
                currentScale = newScale
                updatePreviewTransform()
                scheduleViewportRender()
                return true
            }

            override fun onScaleEnd(detector: ScaleGestureDetector) {
                removeCallbacks(viewportRenderAction)
                viewportRenderScheduled = false
                renderViewport()
            }
        }
    )

    init {
        handles = Array(2) { index -> SelectionHandle(context, if (index == 0) 0 else 3) }
        addView(
            surface,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
        )
        handles.forEach { addView(it) }
        isClickable = true
    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            offsetX = scroller.currX.toDouble()
            offsetY = scroller.currY.toDouble()
            updatePreviewTransform()
            scheduleViewportRender()
            postInvalidateOnAnimation()
        }
    }

    fun render(
        pageIndex: Int,
        width: Int,
        height: Int,
        scale: Double? = null,
        onComplete: (Int) -> Unit = {}
    ) {
        if (pageIndex != currentPage) {
            offsetX = 0.0
            offsetY = 0.0
            renderedOffsetX = 0.0
            renderedOffsetY = 0.0
            currentScale = scale ?: DEFAULT_SCALE
        } else if (scale != null) {
            currentScale = scale
        }
        currentPage = pageIndex
        renderViewport(onComplete)
    }

    fun refresh(onComplete: (Int) -> Unit = {}) {
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
                offsetX = offsetX.toInt(),
                offsetY = offsetY.toInt()
            ),
            onBitmapReady = { bitmap ->
                renderedOffsetX = requestedOffsetX
                renderedOffsetY = requestedOffsetY
                renderedScale = requestedScale
                show(bitmap)
                updatePreviewTransform()
                updateSelectionHandles()
            },
            onPageSize = { size ->
                contentSize = size
                offsetX = offsetX.coerceIn(0.0, (size.x - width).coerceAtLeast(0).toDouble())
                offsetY = offsetY.coerceIn(0.0, (size.y - height).coerceAtLeast(0).toDouble())
            },
            onComplete = onComplete
        )
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        scaleDetector.onTouchEvent(event)
        gestureDetector.onTouchEvent(event)
        return super.dispatchTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (isDraggingHandle) return true
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                scroller.forceFinished(true)
                lastX = event.x
                lastY = event.y
                dragging = false
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                dragging = false
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (scaleDetector.isInProgress ||
                    event.pointerCount > 1 ||
                    kotlin.math.abs(event.x - lastX) > touchSlop ||
                    kotlin.math.abs(event.y - lastY) > touchSlop
                ) return true
            }
        }
        return super.onInterceptTouchEvent(event)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                scroller.forceFinished(true)
                lastX = event.x
                lastY = event.y
                dragging = false
                parent?.requestDisallowInterceptTouchEvent(true)
                return true
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                dragging = false
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (scaleDetector.isInProgress || event.pointerCount > 1) return true
                
                val dx = lastX - event.x
                val dy = lastY - event.y
                if (!dragging && (kotlin.math.abs(dx) > touchSlop || kotlin.math.abs(dy) > touchSlop)) {
                    dragging = true
                }
                if (dragging) {
                    offsetX = (offsetX + dx).coerceIn(
                        0.0, (contentSize.x - width).coerceAtLeast(0).toDouble()
                    )
                    offsetY = (offsetY + dy).coerceIn(
                        0.0, (contentSize.y - height).coerceAtLeast(0).toDouble()
                    )
                    lastX = event.x
                    lastY = event.y
                    updatePreviewTransform()
                    scheduleViewportRender()
                }
                return true
            }
            MotionEvent.ACTION_POINTER_UP -> {
                val upIndex = event.actionIndex
                val remainingIndex = if (upIndex == 0) 1 else 0
                if (remainingIndex < event.pointerCount) {
                    lastX = event.getX(remainingIndex)
                    lastY = event.getY(remainingIndex)
                }
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                removeCallbacks(viewportRenderAction)
                viewportRenderScheduled = false
                
                if (dragging && !scaleDetector.isInProgress) {
                    renderViewport()
                }
                dragging = false
                parent?.requestDisallowInterceptTouchEvent(false)
                performClick()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun selectCellAt(viewX: Float, viewY: Float) {
        val documentZoom = currentScale.coerceAtLeast(MIN_SCALE)
        val pageX = (viewX + offsetX) / documentZoom
        val pageY = (viewY + offsetY) / documentZoom
        if (renderer.selectCell(pageX, pageY)) {
            onCellSelected?.invoke()
            renderViewport()
        }
    }

    private fun extendSelectionTo(viewX: Float, viewY: Float, handleKind: Int = -1) {
        val documentZoom = currentScale.coerceAtLeast(MIN_SCALE)
        val pageX = (viewX + offsetX) / documentZoom
        val pageY = (viewY + offsetY) / documentZoom
        
        val mode = when (handleKind) {
            0 -> 0 // Move Start
            3 -> 1 // Move End
            else -> 2 // General extend
        }
        
        if (renderer.extendSelection(pageX, pageY, mode)) {
            onCellSelected?.invoke()
            // Update handles position instantly
            updateSelectionHandles()
            // Render the blue highlight overlay as fast as possible
            if (isDraggingHandle) renderViewport() else scheduleViewportRender()
        }
    }

    private val viewportRenderAction = Runnable {
        viewportRenderScheduled = false
        renderViewport()
    }

    private fun scheduleViewportRender() {
        if (viewportRenderScheduled) return
        viewportRenderScheduled = true
        postDelayed(viewportRenderAction, 16L)
    }

    private fun updatePreviewTransform() {
        val previewScale = (currentScale / renderedScale).toFloat()
        surface.pivotX = 0f
        surface.pivotY = 0f
        surface.scaleX = previewScale
        surface.scaleY = previewScale
        surface.translationX = (renderedOffsetX * previewScale - offsetX).toFloat()
        surface.translationY = (renderedOffsetY * previewScale - offsetY).toFloat()
        updateSelectionHandles()
    }

    private fun updateSelectionHandles() {
        if (isDraggingHandle) return // Don't let native snapping fight with current drag

        val limits = renderer.getSelectionLimits()
        val box = limits?.getBox()
        if (limits == null || box == null || !limits.getIsActive()) {
            handles.forEach { it.visibility = GONE }
            return
        }

        val factor = currentScale.toFloat()
        val left = (box.left * factor - offsetX).toFloat()
        val top = (box.top * factor - offsetY).toFloat()
        val right = (box.right * factor - offsetX).toFloat()
        val bottom = (box.bottom * factor - offsetY).toFloat()

        positionHandle(handles[0], left, top)
        positionHandle(handles[1], right, bottom)
    }

    private fun positionHandle(handle: View, x: Float, y: Float) {
        val size = (24 * resources.displayMetrics.density)
        handle.translationX = x - size / 2f
        handle.translationY = y - size / 2f
        handle.visibility = VISIBLE
    }

    private inner class SelectionHandle(context: Context, val kind: Int) : View(context) {
        init {
            val size = (24 * resources.displayMetrics.density).toInt()
            layoutParams = LayoutParams(size, size)
            val drawable = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setColor(0xFF2196F3.toInt())
                setStroke((2 * resources.displayMetrics.density).toInt(), Color.WHITE)
            }
            background = drawable
            visibility = GONE
        }

        private var lastRawX = 0f
        private var lastRawY = 0f

        @SuppressLint("ClickableViewAccessibility")
        override fun onTouchEvent(event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastRawX = event.rawX
                    lastRawY = event.rawY
                    isDraggingHandle = true
                    parent.requestDisallowInterceptTouchEvent(true)
                }
                MotionEvent.ACTION_MOVE -> {
                    val dx = event.rawX - lastRawX
                    val dy = event.rawY - lastRawY
                    
                    translationX += dx
                    translationY += dy
                    
                    // Use handle's own size for center calculation
                    val size = (24 * resources.displayMetrics.density)
                    val centerX = translationX + size / 2f
                    val centerY = translationY + size / 2f
                    
                    extendSelectionTo(centerX, centerY, kind)
                    
                    lastRawX = event.rawX
                    lastRawY = event.rawY
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    isDraggingHandle = false
                    parent.requestDisallowInterceptTouchEvent(false)
                    renderViewport()
                }
            }
            return true
        }
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

    fun releaseCurrentPage() {
        renderer.releaseCachedPage()
    }

    fun commitAndReselectCell(onComplete: () -> Unit): Boolean =
        renderer.commitAndReselectCell(onComplete)


    fun close() {
        removeCallbacks(viewportRenderAction)
        renderer.close()
        surface.clearBitmap()
    }

    companion object {
        private const val DEFAULT_SCALE = 1.2
        private const val MIN_SCALE = 0.1
        private const val MAX_SCALE = 8.0
    }
}
