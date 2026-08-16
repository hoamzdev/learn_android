package com.hwa.excelllll.editor

import android.graphics.Bitmap
import android.graphics.RectF
import android.graphics.Point
import android.graphics.PointF
import android.os.Handler
import android.os.Looper
import com.hwa.excelllll.core.InterfaceC1335o
import com.hwa.excelllll.SOBitmap
import com.hwa.excelllll.SODoc
import com.hwa.excelllll.SOPageListener
import com.hwa.excelllll.SORender
import com.hwa.excelllll.SOPage
import com.hwa.excelllll.SOSelectionLimits
import androidx.core.graphics.createBitmap

/**
 * Coordinates a single native page render.
 *
 * This class does not own document opening. SODoc must already be valid.
 */
class ExcelRenderController(
    private val document: SODoc,
    private val bitmapProvider: ExcelBitmapProvider
) {
    private val mainHandler = Handler(Looper.getMainLooper())
    private var activeRender: SORender? = null
    private var renderGeneration = 0
    private var cachedPage: SOPage? = null
    private var cachedPageIndex = -1
    private var renderBuffers = arrayOfNulls<SOBitmap>(2)
    private var displayedBufferIndex = -1
    private var bufferWidth = 0
    private var bufferHeight = 0
    
    private var selectionStart: PointF? = null
    private var selectionEnd: PointF? = null

    /** Selects a cell on the page currently held by the renderer. */
    fun selectCell(pageX: Double, pageY: Double): Boolean {
        val page = cachedPage ?: return false
        document.clearSelection()
        page.select(3, pageX, pageY)
        val point = PointF(pageX.toFloat(), pageY.toFloat())
        selectionStart = point
        selectionEnd = point
        return true
    }

    /** Extends current selection to the specified point using the given mode. */
    fun extendSelection(pageX: Double, pageY: Double, mode: Int = 2): Boolean {
        val page = cachedPage ?: return false
        page.select(mode, pageX, pageY)
        
        val point = PointF(pageX.toFloat(), pageY.toFloat())
        if (mode == 0) {
            selectionStart = point
        } else {
            selectionEnd = point
        }
        return true
    }

    fun getSelectionLimits(): SOSelectionLimits? = cachedPage?.selectionLimits()

    /** Commits the native cell edit buffer while keeping the same selection. */
    fun commitAndReselectCell(onComplete: () -> Unit): Boolean {
        val page = cachedPage ?: return false
        val start = selectionStart ?: return false
        val end = selectionEnd

        // Force commit by selecting a point definitely outside the current cell
        val limits = page.selectionLimits()
        val box = limits?.getBox()
        val temporaryX = if (box != null && box.right > box.left) box.right.toDouble() + 10.0 else start.x.toDouble() + 10.0
        val temporaryY = start.y.toDouble()
        
        page.select(3, temporaryX, temporaryY)
        
        mainHandler.postDelayed({
            if (page !== cachedPage) return@postDelayed
            
            if (end != null) {
                // Restore range using mode 0 (Start) and 1 (End)
                page.select(0, start.x.toDouble(), start.y.toDouble())
                page.select(1, end.x.toDouble(), end.y.toDouble())
            } else {
                // Restore single cell using mode 3 (Caret)
                page.select(3, start.x.toDouble(), start.y.toDouble())
            }
            onComplete()
        }, CELL_COMMIT_DELAY_MS)
        return true
    }

    fun releaseCachedPage() {
        cancel()
        cachedPage?.let { runCatching { it.discard() } }
        cachedPage = null
        cachedPageIndex = -1
        selectionStart = null
        selectionEnd = null
    }

    fun render(
        request: ExcelRenderRequest,
        onBitmapReady: (SOBitmap) -> Unit,
        onPageSize: (Point) -> Unit = {},
        onComplete: (Int) -> Unit = {}
    ) {
        cancel()

        val generation = renderGeneration
        renderWhenPageIsReady(request, onBitmapReady, onPageSize, onComplete, generation)
    }

    private fun renderWhenPageIsReady(
        request: ExcelRenderRequest,
        onBitmapReady: (SOBitmap) -> Unit,
        onPageSize: (Point) -> Unit,
        onComplete: (Int) -> Unit,
        generation: Int,
        attempt: Int = 0
    ) {
        if (generation != renderGeneration) return

        val page = if (cachedPageIndex == request.pageIndex) {
            cachedPage
        } else {
            cachedPage?.let { runCatching { it.discard() } }
            cachedPage = null
            cachedPageIndex = -1
            document.getPage(
                request.pageIndex,
                object : SOPageListener {
                    override fun update(rect: RectF) = Unit
                }
            )?.also {
                cachedPage = it
                cachedPageIndex = request.pageIndex
            }
        }
        if (page == null) {
            if (attempt < MAX_PAGE_READY_ATTEMPTS) {
                mainHandler.postDelayed(
                    {
                        renderWhenPageIsReady(
                            request,
                            onBitmapReady,
                            onPageSize,
                            onComplete,
                            generation,
                            attempt + 1
                        )
                    },
                    PAGE_READY_RETRY_DELAY_MS
                )
            } else {
                onComplete(PAGE_UNAVAILABLE)
            }
            return
        }

        val bitmapWidth = request.width.coerceAtLeast(1)
        val bitmapHeight = request.height.coerceAtLeast(1)
        if (bitmapWidth != bufferWidth || bitmapHeight != bufferHeight) {
            renderBuffers = arrayOfNulls(2)
            bufferWidth = bitmapWidth
            bufferHeight = bitmapHeight
            displayedBufferIndex = -1
        }
        val bufferIndex = if (displayedBufferIndex == 0) 1 else 0
        val soBitmap = renderBuffers[bufferIndex] ?: bitmapProvider.create(
            createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.RGB_565)
        ).also { renderBuffers[bufferIndex] = it }
        soBitmap.bitmap().eraseColor(android.graphics.Color.WHITE)
        
        val renderZoom = (BASE_DOCUMENT_ZOOM * request.scale).takeIf {
            it.isFinite() && it > 0.0
        } ?: request.scale.coerceAtLeast(1.0)
        onPageSize(page.sizeAtZoom(renderZoom))

        var frameDelivered = false
        activeRender = page.render(
            renderZoom,
            android.graphics.PointF(-request.offsetX.toFloat(), -request.offsetY.toFloat()),
            soBitmap,
            InterfaceC1335o { code ->
                if (generation != renderGeneration || frameDelivered) {
                    return@InterfaceC1335o
                }
                if (code == 0) {
                    frameDelivered = true
                    displayedBufferIndex = bufferIndex
                    onBitmapReady(soBitmap)
                }
                onComplete(code)
            }
        )
    }

    fun cancel() {
        renderGeneration++
        mainHandler.removeCallbacksAndMessages(null)
        activeRender?.let {
            try {
                it.abort()
            } catch (_: Throwable) {
            }

            try {
                it.destroy()
            } catch (_: Throwable) {
            }
        }
        activeRender = null
    }

    fun close() {
        releaseCachedPage()
        renderBuffers = arrayOfNulls(2)
    }


    companion object {
        const val PAGE_UNAVAILABLE = -1
        private const val BASE_DOCUMENT_ZOOM = 1.0
        private const val MAX_PAGE_READY_ATTEMPTS = 20
        private const val PAGE_READY_RETRY_DELAY_MS = 50L
        private const val CELL_COMMIT_DELAY_MS = 50L
    }
}
