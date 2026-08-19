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
    
    private var lastStableLimits: SOSelectionLimits? = null
    private var isCommitInProgress = false

    /** Selects a cell and locks its coordinates to prevent neighbor editing. */
    fun selectCell(pageX: Double, pageY: Double): Boolean {
        if (isCommitInProgress) return false
        val page = cachedPage ?: return false
        
        document.clearSelection()
        page.select(3, pageX, pageY)
        
        val limits = page.selectionLimits()
        val box = limits?.getBox()
        if (limits != null && box != null && limits.getIsActive()) {
            // Khóa vào tâm ô để tuyệt đối không chạm vào ô bên cạnh
            val centerX = (box.left + box.right) / 2.0
            val centerY = (box.top + box.bottom) / 2.0
            val stablePoint = PointF(centerX.toFloat(), centerY.toFloat())
            selectionStart = stablePoint
            selectionEnd = stablePoint
            lastStableLimits = limits
        } else {
            val point = PointF(pageX.toFloat(), pageY.toFloat())
            selectionStart = point
            selectionEnd = point
        }
        return true
    }

    fun extendSelection(pageX: Double, pageY: Double, mode: Int = 2): Boolean {
        if (isCommitInProgress) return false
        val page = cachedPage ?: return false
        page.select(mode, pageX, pageY)
        
        val limits = page.selectionLimits()
        val box = limits?.getBox()
        if (limits != null && box != null && limits.getIsActive()) {
            if (mode == 0) {
                selectionStart = PointF(box.left + 1f, box.top + 1f)
            } else {
                selectionEnd = PointF(box.right - 1f, box.bottom - 1f)
            }
            lastStableLimits = limits
        }
        return true
    }

    fun isBusy(): Boolean = isCommitInProgress

    fun getSelectionLimits(): SOSelectionLimits? {
        if (isCommitInProgress) return lastStableLimits
        val limits = cachedPage?.selectionLimits()
        if (limits != null && limits.getIsActive()) {
            lastStableLimits = limits
        }
        return limits
    }

    /** Commits and re-selects the LOCKED coordinates. */
    fun commitAndReselectCell(onComplete: () -> Unit): Boolean {
        val page = cachedPage ?: return false
        val start = selectionStart ?: return false
        val end = selectionEnd ?: return false

        isCommitInProgress = true

        // Re-select lại tọa độ cũ để engine chốt dữ liệu. 
        // Thực hiện đồng bộ (Sync) để tránh mất nhịp lệnh edit tiếp theo.
        if (start.x != end.x || start.y != end.y) {
            page.select(0, start.x.toDouble(), start.y.toDouble())
            page.select(1, end.x.toDouble(), end.y.toDouble())
        } else {
            page.select(3, start.x.toDouble(), start.y.toDouble())
        }
        
        isCommitInProgress = false
        onComplete()

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
            document.getPage(request.pageIndex, object : SOPageListener {
                override fun update(rect: RectF) = Unit
            })?.also {
                cachedPage = it
                cachedPageIndex = request.pageIndex
            }
        }
        
        if (page == null) {
            if (attempt < 20) {
                mainHandler.postDelayed({
                    renderWhenPageIsReady(request, onBitmapReady, onPageSize, onComplete, generation, attempt + 1)
                }, 50L)
            } else onComplete(-1)
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
            createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888)
        ).also { renderBuffers[bufferIndex] = it }
        soBitmap.bitmap().eraseColor(android.graphics.Color.WHITE)
        
        // Cập nhật tọa độ vùng vẽ
        soBitmap.left = 0
        soBitmap.top = 0
        soBitmap.right = bitmapWidth
        soBitmap.bottom = bitmapHeight
        soBitmap.syncRect()
        
        val renderZoom = (1.0 * request.scale).takeIf { it.isFinite() && it > 0.0 } ?: request.scale.coerceAtLeast(1.0)
        onPageSize(page.sizeAtZoom(renderZoom))

        var frameDelivered = false
        activeRender = page.render(
            renderZoom,
            PointF(-request.offsetX.toFloat(), -request.offsetY.toFloat()),
            soBitmap,
            InterfaceC1335o { code ->
                if (generation != renderGeneration || frameDelivered) return@InterfaceC1335o
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
            runCatching { it.abort() }
            runCatching { it.destroy() }
        }
        activeRender = null
    }

    fun close() {
        releaseCachedPage()
        renderBuffers = arrayOfNulls(2)
    }

    companion object {
        private const val CELL_COMMIT_DELAY_MS = 10L
    }
}
