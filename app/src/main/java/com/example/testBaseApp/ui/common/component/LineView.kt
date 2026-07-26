package com.example.testBaseApp.ui.common.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class LineView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // 1. Tạo Paint cho đường thẳng thường (đầu tròn)
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        strokeWidth = 10f // Độ dày đường thẳng (pixel)
        strokeCap = Paint.Cap.ROUND // Làm tròn 2 đầu đường thẳng
    }

    // 2. Tạo Paint cho đường nét đứt (Dashed Line)
    private val dashedPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
        strokeWidth = 6f
        style = Paint.Style.STROKE
        // Mảng [độ dài đoạn hiện, độ dài đoạn ẩn]
        pathEffect = DashPathEffect(floatArrayOf(20f, 10f), 0f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Ví dụ 1: Vẽ đường nằm ngang chính giữa View
        val startX1 = paddingLeft.toFloat()
        val startY1 = height / 2f
        val stopX1 = width - paddingRight.toFloat()
        val stopY1 = height / 2f

        canvas.drawLine(startX1, startY1, stopX1, stopY1, linePaint)

        // Ví dụ 3: Vẽ đường nét đứt nằm ngang (cách mép trên 50px)
        canvas.drawLine(
            0f, 50f,
            width.toFloat(), 50f,
            dashedPaint
        )
    }
}