package com.example.testBaseApp.component

import android.os.SystemClock
import android.view.View

/**
 * @author hwa..
 */

fun View.setOnSingleListener(
    duration: Long = 300L,
    internet : Boolean = true,
    block: (View) -> Unit,
) {
    var lastClickTime = 0L
    setOnClickListener {
        val currentTime = SystemClock.elapsedRealtime()
        if(currentTime - lastClickTime >= duration){
            lastClickTime = currentTime
            block(it)
        }
    }
}