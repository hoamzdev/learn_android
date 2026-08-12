package com.hwa.excelllll.excel

import android.graphics.PointF
import com.hwa.excelllll.core.InterfaceC1335o
import com.hwa.excelllll.SOBitmap
import com.hwa.excelllll.SODoc
import com.hwa.excelllll.SOPage
import com.hwa.excelllll.SORender

/**
 * Thin render coordinator. It deliberately does not duplicate native
 * rendering logic already implemented by SOPage.
 */
class ExcelRenderController(
    private val documentProvider: () -> SODoc?
) {
    private var render: SORender? = null

    fun renderPage(
        page: SOPage,
        layer: Int,
        zoom: Double,
        origin: PointF,
        bitmap: SOBitmap,
        callback: InterfaceC1335o
    ) {
        stop()

        render = page.render(layer, zoom, origin, bitmap, callback, true)
    }

    fun renderPage(
        page: SOPage,
        zoom: Double,
        origin: PointF,
        bitmap: SOBitmap,
        callback: InterfaceC1335o
    ) {
        stop()
        render = page.render(zoom, origin, bitmap, callback)
    }

    fun stop() {
        render?.let {
            try {
                it.abort()
            } catch (_: Throwable) {
            }

            try {
                it.destroy()
            } catch (_: Throwable) {
            }
        }
        render = null
    }

    fun isRendering(): Boolean = render != null

    fun release() {
        stop()
    }
}
