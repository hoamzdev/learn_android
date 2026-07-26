package com.example.testBaseApp.ui.common.component

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.testBaseApp.R
import androidx.core.content.withStyledAttributes

@SuppressLint("Recycle", "CustomViewStyleable")
class CustomLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    deffStyle : Int = 0
) : View(context, attributeSet, deffStyle){

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }
    private var circleColor = Color.RED
    private var radius = 100f
    private var strokeWidth = 5f
    var preColor = Color.TRANSPARENT

    init {
        attributeSet?.let {
            context.withStyledAttributes(it, R.styleable.CustomLayout) {
                circleColor = getColor(R.styleable.CustomLayout_circleColor, Color.RED)
                radius = getDimension(R.styleable.CustomLayout_circleRadius, 100f)
                strokeWidth = getDimension(R.styleable.CustomLayout_strokeWidth, 5f)
            }
        }
        paint.color = circleColor
        paint.strokeWidth = strokeWidth
        preColor = circleColor
        setOnClickListener {
            circleColor = if(circleColor != Color.RED) Color.RED else preColor
            paint.color = circleColor
            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val dW = (2 * radius + paddingLeft + paddingRight).toInt()
        val dH = (2 * radius + paddingTop + paddingBottom).toInt()

        val width = resolveSize(dW, widthMeasureSpec)
        val height = resolveSize(dH, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //tinh tam dua tren view cua khai bao
        val centerX = width / 2f
        val centerY = height / 2f
        canvas.drawCircle(centerX, centerY, radius,paint)
    }
}