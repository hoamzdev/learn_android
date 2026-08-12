package com.hwa.excelllll.core

import android.content.Context
import com.hwa.excelllll.SOLib

/**
 * Application-level lifecycle for the native Artifex engine.
 */
object DocumentEngine {
    @Volatile
    private var initialized = false

    @Synchronized
    fun initialize(context: Context) {
        if (initialized) return
        SOLib.init(context.applicationContext)
        initialized = true
    }

    @Synchronized
    fun shutdown() {
        if (!initialized) return
        SOLib.terminate()
        initialized = false
    }

    fun isInitialized(): Boolean = initialized
}
