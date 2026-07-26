package com.example.testBaseApp.ui.common.component

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.DiscretePathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.testBaseApp.R
import kotlin.properties.Delegates

class CustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 5f
        //ve net dut
//        pathEffect = DashPathEffect(
//            floatArrayOf(10f,10f),
//            0f
//        )
        textSize = 36f.sp
        typeface = Typeface.DEFAULT_BOLD
        textAlign = Paint.Align.CENTER
    }

    private val path = Path()
    private var x : Float = (width / 2).toFloat()
    private var y : Float = (height / 2).toFloat()


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //ve 1 diem tai toa do (x,y)
        /*canvas.drawPoint(
            100f,
            100f,
            paint
        )*/
        //ve 1 duong thang bat dau tu (x,y) -> (x1,y1)
        /*val startX = 100f
        val startY = 100f

        val stopX = width - 100f
        val stopY = 100f
        canvas.drawLine(startX, startY, stopX, stopY, paint)
        canvas.drawLine(startX,startY,startX,height - startY,paint)
        canvas.drawLine(stopX,stopY,stopX,height - stopY, paint)
        canvas.drawLine(startX,height - startY,width - startX,height - startY,paint)*/

        //ve hinh chu nhat co bo goc
//        canvas.drawRoundRect(
//            100f,
//            100f,
//            width - 100f,
//            500f,
//            20f,
//            20f,
//            paint
//        )

        //ve hinh elip, hinh tron
//        canvas.drawOval(
//            100f,
//            100f,
//            width - 100f,
//            400f,
//            paint
//        )
        //ve hinh tron
        /*
        canvas.drawCircle(
            width / 2f,
            100f,
            100f,
            paint
        )*/

        /*canvas.drawArc(
            (width - paddingRight - 200).toFloat(),
            paddingTop.toFloat(),
            (width - paddingRight).toFloat(),
            (paddingTop + 200).toFloat(),
            270f,
            180f,
            false,
            paint
        )*/

        //ve path
//        canvas.drawPoint(100f, 100f, paint)
//        canvas.drawPoint(400f, 100f,paint)
//        canvas.drawPoint((100f + 400f) / 2, 200f, paint)
//
//        path.moveTo(100f,100f)
//        path.quadTo(250f, 200f, 400f, 100f)
//        path.close()
//        canvas.drawPath(path,paint)
        canvas.drawCircle(x,y,20f, paint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event?.action == MotionEvent.ACTION_DOWN || event?.action == MotionEvent.ACTION_MOVE){
            x = event.x
            y = event.y
            invalidate()
        }
        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        android.util.Log.d("CustomView", "size = $w x $h")
    }
}

val Float.sp : Float
    get() = this * Resources.getSystem().displayMetrics.scaledDensity