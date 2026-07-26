package com.example.testBaseApp.ui.common.component

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.testBaseApp.R
import androidx.core.content.withStyledAttributes
import kotlin.math.min

@SuppressLint("Recycle")
class CircleProgressView @JvmOverloads constructor(
    context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attributes, defStyleAttr) {

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val rectF = RectF()
    var progress: Int = 50
        set(value) {
            field = value.coerceIn(0, 100)
            invalidate()
        }
    private var strokeWidth = 20f

    init {
        attributes?.let {
            context.withStyledAttributes(it, R.styleable.CircleProgressView) {
                bgPaint.color = getColor(
                    R.styleable.CircleProgressView_backgroundColor, Color.GRAY
                )
                progressPaint.color = getColor(
                    R.styleable.CircleProgressView_progressColor, Color.BLUE
                )
                progress = getInt(R.styleable.CircleProgressView_progress, 50)
            }
        }
        bgPaint.strokeWidth = strokeWidth
        progressPaint.strokeWidth = strokeWidth
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val dWidth = 200
        val dHeight = 200
        val width = resolveSize(dWidth, widthMeasureSpec)
        val height = resolveSize(dHeight, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val size = min(width, height)
        val halfStroke = strokeWidth

        rectF.set(
            paddingLeft + halfStroke,
            paddingTop + halfStroke,
            size - paddingRight - halfStroke,
            size - paddingBottom - halfStroke
        )

        canvas.drawArc(rectF, 0f, 360f, false, bgPaint)

        val sweepAngle = (progress / 100f) * 360f
        canvas.drawArc(rectF, 90f, sweepAngle, false, progressPaint)
    }
}