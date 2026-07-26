package com.example.testBaseApp.ui.common.component

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.withStyledAttributes
import com.example.testBaseApp.R
import java.util.Locale
import kotlin.math.min

/**
 * A custom circular countdown timer view with centered formatted text display.
 * Supports smooth animation, pause/resume, and programmatic or XML attribute customization.
 */
class CircularTimerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Paints
    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val mainTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
    }

    private val subTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
    }

    // Geometry
    private val rectF = RectF()
    private val textBounds = Rect()

    // Configurable Properties
    var strokeWidth: Float = 16f
        set(value) {
            field = value
            bgPaint.strokeWidth = value
            progressPaint.strokeWidth = value
            invalidate()
        }

    var progressRatio: Float = 1.0f // 1.0f = full, 0.0f = empty
        set(value) {
            field = value.coerceIn(0f, 1f)
            invalidate()
        }

    var maxDurationMs: Long = 60_000L
    var remainingTimeMs: Long = maxDurationMs
        private set

    var customText: String? = null
        set(value) {
            field = value
            invalidate()
        }

    var subText: String = "REMAINING"
        set(value) {
            field = value
            invalidate()
        }

    // Animation & State
    private var animator: ValueAnimator? = null
    var isRunning: Boolean = false
        private set

    private var onTickListener: ((remainingMs: Long) -> Unit)? = null
    private var onFinishListener: (() -> Unit)? = null

    init {
        var bgColor = Color.parseColor("#E0E0E0")
        var progressColor = Color.parseColor("#FF5722")
        var textColor = Color.parseColor("#212121")
        var subTextColor = Color.parseColor("#757575")
        var textSizePx = 48f
        var subTextSizePx = 24f

        attrs?.let {
            context.withStyledAttributes(it, R.styleable.CircularTimerView) {
                bgColor = getColor(R.styleable.CircularTimerView_timerBackgroundColor, bgColor)
                progressColor = getColor(R.styleable.CircularTimerView_timerProgressColor, progressColor)
                textColor = getColor(R.styleable.CircularTimerView_timerTextColor, textColor)
                subTextColor = getColor(R.styleable.CircularTimerView_timerSubTextColor, subTextColor)
                strokeWidth = getDimension(R.styleable.CircularTimerView_timerStrokeWidth, 16f)
                textSizePx = getDimension(R.styleable.CircularTimerView_timerTextSize, 48f)
                subTextSizePx = getDimension(R.styleable.CircularTimerView_timerSubTextSize, 24f)
                maxDurationMs = getInt(R.styleable.CircularTimerView_timerMaxDurationMs, 60_000).toLong()
            }
        }

        bgPaint.apply {
            color = bgColor
            this.strokeWidth = strokeWidth
        }

        progressPaint.apply {
            color = progressColor
            this.strokeWidth = strokeWidth
        }

        mainTextPaint.apply {
            color = textColor
            textSize = textSizePx
            isFakeBoldText = true
        }

        subTextPaint.apply {
            color = subTextColor
            textSize = subTextSizePx
        }

        remainingTimeMs = maxDurationMs
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val defaultSize = 300
        val width = resolveSize(defaultSize, widthMeasureSpec)
        val height = resolveSize(defaultSize, heightMeasureSpec)
        val size = min(width, height)
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val halfStroke = strokeWidth / 2f
        rectF.set(
            paddingLeft + halfStroke,
            paddingTop + halfStroke,
            width - paddingRight - halfStroke,
            height - paddingBottom - halfStroke
        )

        // 1. Draw Background Ring
        canvas.drawArc(rectF, 0f, 360f, false, bgPaint)

        // 2. Draw Progress Arc (starts from top -90 deg)
        val sweepAngle = progressRatio * 360f
        canvas.drawArc(rectF, -90f, sweepAngle, false, progressPaint)

        // 3. Draw Centered Timer Text
        val textToDraw = customText ?: formatTime(remainingTimeMs)
        val centerX = width / 2f
        val centerY = height / 2f

        mainTextPaint.getTextBounds(textToDraw, 0, textToDraw.length, textBounds)
        val mainTextY = centerY - (mainTextPaint.descent() + mainTextPaint.ascent()) / 2f - 10f
        canvas.drawText(textToDraw, centerX, mainTextY, mainTextPaint)

        // 4. Draw Centered Subtitle Text
        if (subText.isNotEmpty()) {
            val subTextY = mainTextY + subTextPaint.textSize + 8f
            canvas.drawText(subText, centerX, subTextY, subTextPaint)
        }
    }

    /**
     * Starts the countdown timer from current remaining time or custom duration.
     */
    fun startTimer(
        durationMs: Long = remainingTimeMs,
        onTick: ((remainingMs: Long) -> Unit)? = null,
        onFinish: (() -> Unit)? = null
    ) {
        animator?.cancel()

        this.onTickListener = onTick
        this.onFinishListener = onFinish
        this.maxDurationMs = if (durationMs > 0) durationMs else maxDurationMs
        this.remainingTimeMs = durationMs

        animator = ValueAnimator.ofFloat(remainingTimeMs.toFloat(), 0f).apply {
            duration = remainingTimeMs
            interpolator = LinearInterpolator()
            addUpdateListener { anim ->
                val currentMs = (anim.animatedValue as Float).toLong()
                remainingTimeMs = currentMs
                progressRatio = currentMs.toFloat() / maxDurationMs.toFloat()
                onTickListener?.invoke(remainingTimeMs)
            }
        }

        animator?.addListener(object : android.animation.AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: android.animation.Animator) {
                isRunning = false
                remainingTimeMs = 0L
                progressRatio = 0f
                onFinishListener?.invoke()
            }
        })

        animator?.start()
        isRunning = true
    }

    /**
     * Pauses the ongoing timer.
     */
    fun pauseTimer() {
        if (isRunning) {
            animator?.pause()
            isRunning = false
        }
    }

    /**
     * Resumes the paused timer.
     */
    fun resumeTimer() {
        if (!isRunning && remainingTimeMs > 0) {
            animator?.resume()
            isRunning = true
        }
    }

    /**
     * Resets the timer back to maximum duration.
     */
    fun resetTimer(durationMs: Long = maxDurationMs) {
        animator?.cancel()
        isRunning = false
        maxDurationMs = durationMs
        remainingTimeMs = durationMs
        progressRatio = 1.0f
        customText = null
    }

    private fun formatTime(millis: Long): String {
        val totalSeconds = millis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator?.cancel()
    }
}
