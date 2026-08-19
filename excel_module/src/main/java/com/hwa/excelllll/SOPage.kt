package com.hwa.excelllll

import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.PointF
import android.graphics.RectF
import com.hwa.excelllll.core.InterfaceC1335o
import androidx.annotation.Keep

@Keep
open class SOPage(
    @JvmField protected var internal: Long
) {
    /** 
     * Signature 12 tham số chuẩn cho engine abc/v8a:
     * layer, zoom, x, y, bitmap, bitmapWidth, overlay, left, top, right, bottom, listener
     */
    private external fun nativeRenderAtZoom(
        layer: Int,
        zoom: Double,
        originX: Double,
        originY: Double,
        bitmap: Bitmap,
        bitmapWidth: Int,       // Tham số thứ 6
        overlay: Bitmap?,      // Tham số thứ 7
        left: Int,
        top: Int,
        right: Int,
        bottom: Int,
        listener: SORenderListenerInternal
    ): SORender?

    external fun discard()
    external fun getHorizontalRuler(): FloatArray?
    external fun getPageTitle(): String?
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
        target.syncRect()
        
        return nativeRenderAtZoom(
            layer,
            zoom,
            origin.x.toDouble(),
            origin.y.toDouble(),
            target.bitmap(),
            target.bitmap().width, // Truyền bitmapWidth vào vị trí thứ 6
            null,                  // Overlay = null ở vị trí thứ 7
            target.left,
            target.top,
            target.right,
            target.bottom,
            object : SORenderListenerInternal {
                override fun progress(status: Int) {
                    if (dispatchOnMainThread) {
                        SOLib.postToUi { listener.onRenderComplete(status) }
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
