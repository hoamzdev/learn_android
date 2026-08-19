package com.hwa.excelllll

import android.graphics.Bitmap
import android.graphics.Rect
import androidx.annotation.Keep

@Keep
open class SOBitmap(
    @JvmField val bitmap: Bitmap,
    @JvmField var left: Int,
    @JvmField var top: Int,
    @JvmField var right: Int,
    @JvmField var bottom: Int
) {
    @JvmField
    @Keep
    var rect: Rect = Rect(left, top, right, bottom)

    constructor(bitmap: Bitmap) : this(bitmap, 0, 0, bitmap.width, bitmap.height)

    constructor(width: Int, height: Int) : this(
        Bitmap.createBitmap(width.coerceAtLeast(1), height.coerceAtLeast(1), Bitmap.Config.ARGB_8888)
    )

    constructor() : this(1, 1)

    // Các hàm native bổ trợ cho bộ đệm pixel
    external fun nativeCopyPixels565(bitmap: Bitmap)
    external fun nativeCopyPixels888(bitmap: Bitmap)
    external fun nativeMergePixels565(bitmap: Bitmap)
    external fun nativeMergePixels888(bitmap: Bitmap)
    external fun invertLuminance()

    fun bitmap(): Bitmap = bitmap
    fun width(): Int = bitmap.width
    fun height(): Int = bitmap.height
    fun bounds(): Rect = rect

    fun crop(left: Int, top: Int, right: Int, bottom: Int): SOBitmap {
        return SOBitmap(
            bitmap,
            left.coerceAtLeast(0),
            top.coerceAtLeast(0),
            right.coerceAtMost(bitmap.width),
            bottom.coerceAtMost(bitmap.height)
        ).apply { syncRect() }
    }

    /** Đảm bảo đối tượng Rect nội bộ luôn đồng bộ với các trường tọa độ */
    fun syncRect() {
        rect.set(left, top, right, bottom)
    }
}
