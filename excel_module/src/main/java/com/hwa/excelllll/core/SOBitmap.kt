package com.hwa.excelllll

import android.graphics.Bitmap
import android.graphics.Rect

open class SOBitmap(
    private val bitmap: Bitmap,
    private val rect: Rect = Rect(0, 0, bitmap.width, bitmap.height)
) {
    constructor(width: Int, height: Int) : this(
        Bitmap.createBitmap(
            width.coerceAtLeast(1),
            height.coerceAtLeast(1),
            Bitmap.Config.RGB_565
        )
    )

    constructor() : this(1, 1)

    fun bitmap(): Bitmap = bitmap
    fun bounds(): Rect = Rect(rect)

    fun width(): Int = bitmap.width
    fun height(): Int = bitmap.height

    fun crop(left: Int, top: Int, right: Int, bottom: Int): SOBitmap {
        val safe = Rect(
            left.coerceAtLeast(0),
            top.coerceAtLeast(0),
            right.coerceAtMost(bitmap.width),
            bottom.coerceAtMost(bitmap.height)
        )
        return SOBitmap(bitmap, safe)
    }
}
