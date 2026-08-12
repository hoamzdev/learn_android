package com.hwa.excelllll.editor

import android.graphics.Bitmap
import android.graphics.RectF
import android.graphics.Point
import android.os.Handler
import android.os.Looper
import com.hwa.excelllll.core.InterfaceC1335o
import com.hwa.excelllll.SOBitmap
import com.hwa.excelllll.SODoc
import com.hwa.excelllll.SOPageListener
import com.hwa.excelllll.SORender
import com.hwa.excelllll.SOPage
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
    private var selectedPageX: Double? = null
    private var selectedPageY: Double? = null

    /** Selects a cell on the page currently held by the renderer. */
    fun selectCell(pageX: Double, pageY: Double): Boolean {
        val page = cachedPage ?: return false
        document.clearSelection()
        page.select(3, pageX, pageY)
        selectedPageX = pageX
        selectedPageY = pageY
        return true
    }

    /** Commits the native cell edit buffer while keeping the same cell selected. */
    fun commitAndReselectCell(onComplete: () -> Unit): Boolean {
        val page = cachedPage ?: return false
        val pageX = selectedPageX ?: return false
        val pageY = selectedPageY ?: return false

        // libso commits spreadsheet text only when selection enters another
        // cell. clearSelection() alone is not enough, so briefly select a
        // point just outside the current cell and then restore it.
        val box = runCatching { page.selectionLimits()?.getBox() }.getOrNull()
            ?: return false
        val temporaryX = if (box.right > box.left) box.right + 1.0 else pageX + 1.0
        val temporaryY = if (box.bottom > box.top) {
            ((box.top + box.bottom) / 2f).toDouble()
        } else {
            pageY
        }
        page.select(3, temporaryX, temporaryY)
        mainHandler.postDelayed({
            if (page !== cachedPage) return@postDelayed
            page.select(3, pageX, pageY)
            onComplete()
        }, CELL_COMMIT_DELAY_MS)
        return true
    }

    fun releaseCachedPage() {
        cancel()
        cachedPage?.let { runCatching { it.discard() } }
        cachedPage = null
        cachedPageIndex = -1
        selectedPageX = null
        selectedPageY = null
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
            // Artifex can emit onLayoutCompleted just before the first page is
            // exposed by getPage(). Treat that as a short-lived loading state,
            // not as a render failure.
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

        // libso's original rendering pipeline uses RGB_565 render buffers.
        // Supplying ARGB_8888 can produce wrong stride/colour interpretation
        // with older Artifex binaries (faded lines and duplicated pixels).
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
        val bitmap = soBitmap.bitmap().apply {
            eraseColor(android.graphics.Color.WHITE)
        }
        // Use a document-independent base zoom. Fitting the whole sheet to the
        // viewport makes large sheets start with much smaller text than small
        // sheets. A fixed base keeps the initial cell/text size consistent;
        // page dimensions only determine how far the viewport can scroll.
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
                // abort()/destroy() cannot remove a callback that libso has
                // already queued on the main thread. Never let an old zoom or
                // viewport replace the latest rendered bitmap.
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
