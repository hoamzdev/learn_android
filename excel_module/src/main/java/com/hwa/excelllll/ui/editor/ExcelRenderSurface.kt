package com.hwa.excelllll.ui.editor

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View

/**
 * Small rendering surface separated from document/page management.
 */
class ExcelRenderSurface(
    context: android.content.Context
) : View(context) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
    private var bitmap: Bitmap? = null
    private val source = Rect()
    private val destination = Rect()

    fun setBitmap(value: Bitmap?) {
        bitmap = value
        value?.let {
            source.set(0, 0, it.width, it.height)
        }
        invalidate()
    }

    fun clearBitmap() {
        bitmap = null
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val value = bitmap ?: return
        if (value.isRecycled) return

        if (value.width == width && value.height == height) {
            // Preserve a 1:1 pixel mapping for thin spreadsheet text.
            canvas.drawBitmap(value, 0f, 0f, paint)
        } else {
            destination.set(0, 0, width, height)
            canvas.drawBitmap(value, source, destination, paint)
        }
    }

    override fun onDetachedFromWindow() {
        bitmap = null
        super.onDetachedFromWindow()
    }
}
