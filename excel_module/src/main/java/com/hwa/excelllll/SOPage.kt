package com.hwa.excelllll

import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.PointF
import android.graphics.RectF
import androidx.annotation.Keep
import com.hwa.excelllll.core.InterfaceC1335o

@Keep
open class SOPage(
    @JvmField protected var internal: Long
) {
    /**
     * Signature 12 tham số của native engine:
     * layer, zoom, x, y, bitmap, bitmapWidth, overlay, left, top, [right|width], [bottom|height], listener
     *
     * Lưu ý ABI:
     *   - arm64-v8a (libabc.so mới): tham số 10-11 là (right, bottom) = tọa độ tuyệt đối.
     *   - armeabi-v7a (libabc.so cũ): tham số 10-11 là (width, height) = kích thước vùng vẽ.
     * Hàm render() bên dưới tự động detect ABI và truyền đúng giá trị cho từng build.
     */
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
        rightOrWidth: Int,
        bottomOrHeight: Int,
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

        val isV7a = SOLib.isV7a
        val rightOrWidth  = if (isV7a) target.right - target.left else target.right
        val bottomOrHeight = if (isV7a) target.bottom - target.top else target.bottom

        return nativeRenderAtZoom(
            layer,
            zoom,
            origin.x.toDouble(),
            origin.y.toDouble(),
            target.bitmap(),
            target.bitmap().width,
            null,
            target.left,
            target.top,
            rightOrWidth,
            bottomOrHeight,
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
