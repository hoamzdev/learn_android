package com.example.testBaseApp.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.annotation.Keep


import java.util.WeakHashMap
import kotlin.collections.set
import kotlin.let



@Keep
fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}

abstract class TapListener : View.OnClickListener {
    companion object {
        private const val DEFAULT_DELAY = 500L
    }

    private var lastClickMap = WeakHashMap<View, Long>()

    override fun onClick(v: View?) {
        v ?: return
        val now = System.currentTimeMillis()
        val lastClick = lastClickMap[v] ?: 0L
        if (now - lastClick >= DEFAULT_DELAY) {
            lastClickMap[v] = now
            onTap(v)
        }
    }

    abstract fun onTap(v: View?)
}

//tranh click lien tuc
object SafeClickManager {
    private const val CLICK_DELAY = 500L
    private var lastClickTime = 0L

    fun canClick(): Boolean {
        val currentTime = System.currentTimeMillis()
        return if (currentTime - lastClickTime >= CLICK_DELAY) {
            lastClickTime = currentTime
            true
        } else {
            false
        }
    }
}

fun View.onClickNormal(block :() -> Unit){
    this.setOnClickListener {
        block()
    }
}