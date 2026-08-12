package com.hwa.excelllll.core

/**
 * Cleaner names for the application-facing callbacks.
 * These do not participate in JNI directly.
 */
fun interface RenderListener {
    fun onRenderComplete(status: Int)
}

fun interface SaveListener {
    fun onComplete(result: Int, error: Int)
}
