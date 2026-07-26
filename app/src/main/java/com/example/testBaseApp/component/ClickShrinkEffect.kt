package com.example.testBaseApp.component

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View

/**
 * @author hwa..
 */

private const val SHRINK_VALUE = 0.93f
private const val DURATION_ANIMATION = 100L


@SuppressLint("ClickableViewAccessibility")
fun View.setOnClickShrinkListener(
    l: View.OnClickListener,
) {
    this.setOnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> buildShinkAnimation().start()
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL,
                -> buildGrowAnimation().start()
        }
        return@setOnTouchListener false
    }
    this.setOnSingleListener {
        l.onClick(this)
    }
}

private fun View.buildShinkAnimation(): Animator {
    val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, SHRINK_VALUE)
    val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, SHRINK_VALUE)
    this.apply {
        val animation = ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY)
        animation.duration = DURATION_ANIMATION
        return animation
    }
}

private fun View.buildGrowAnimation(): Animator {
    val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, SHRINK_VALUE, 1f)
    val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, SHRINK_VALUE, 1f)
    this.apply {
        val animation = ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY)
        animation.duration = DURATION_ANIMATION
        return animation
    }
}