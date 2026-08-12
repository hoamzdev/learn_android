package com.hwa.excelllll

import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.PointF
import android.graphics.RectF
import com.hwa.excelllll.core.InterfaceC1335o

open class SOPage(
    @JvmField protected var internal: Long
) {
    private external fun nativeRenderAtZoom(
        layer: Int,
        zoom: Double,
        originX: Double,
        originY: Double,
        bitmap: Bitmap,
        bitmapWidth: Int,
        overlay: Bitmap?,
        left: Int,
        top: Int,
        width: Int,
        height: Int,
        listener: SORenderListenerInternal
    ): SORender?

    external fun discard()
    external fun getHorizontalRuler(): FloatArray?
    external fun getVerticalRuler(): FloatArray?
    external fun objectAtPoint(x: Float, y: Float): SOHyperlink?
    external fun select(mode: Int, x: Double, y: Double)
    external fun selectionLimits(): SOSelectionLimits?
    external fun setSelectionLimitsBox(left: Float, top: Float, right: Float, bottom: Float)
    external fun sizeAtZoom(zoom: Double): Point
    external fun zoomToFitRect(width: Int, height: Int): PointF

    fun render(
        layer: Int,
        zoom: Double,
        origin: PointF,
        target: SOBitmap,
        listener: InterfaceC1335o,
        dispatchOnMainThread: Boolean = true
    ): SORender? {
        val rect = target.bounds()
        val bitmap = target.bitmap()

        require(rect.left >= 0 && rect.top >= 0)
        require(rect.right <= bitmap.width && rect.bottom <= bitmap.height)

        return nativeRenderAtZoom(
            layer,
            zoom,
            origin.x.toDouble(),
            origin.y.toDouble(),
            bitmap,
            bitmap.width,
            null,
            rect.left,
            rect.top,
            rect.width(),
            rect.height(),
            object : SORenderListenerInternal {
                override fun progress(status: Int) {
                    if (dispatchOnMainThread) {
                        SOLib.postToUi(Runnable { listener.onRenderComplete(status) })
                    } else {
                        listener.onRenderComplete(status)
                    }
                }
            }
        )
    }

    fun render(
        zoom: Double,
        origin: PointF,
        target: SOBitmap,
        listener: InterfaceC1335o
    ): SORender? = render(-2, zoom, origin, target, listener, true)

    fun select(mode: Int, point: PointF) {
        select(mode, point.x.toDouble(), point.y.toDouble())
    }

    fun setSelectionBox(rect: RectF) {
        setSelectionLimitsBox(rect.left, rect.top, rect.right, rect.bottom)
    }
}
