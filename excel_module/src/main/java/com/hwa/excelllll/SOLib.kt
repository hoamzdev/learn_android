package com.hwa.excelllll

import android.content.Context
import android.os.Handler
import android.os.Looper
import java.io.File
import java.util.Locale

/**
 * JNI owner for libso.so.
 *
 * This must be a real class rather than a Kotlin object. The native library
 * accesses the instance field named "internal" through JNI GetFieldID.
 */
class SOLib private constructor() {

    @Suppress("unused")
    private var internal: Long = 0

    private external fun preInitLib()
    private external fun initLib(language: String): Long
    private external fun setTempPath(path: String): Int
    private external fun installFonts(path: String): Int
    private external fun finLib()

    external fun openDocumentInternal(
        path: String,
        listener: SODocLoadListenerInternal
    ): SODoc?

    private fun initialize(context: Context) {
        if (internal != 0L) return

        // Đảm bảo các class JNI quan trọng được nạp vào máy ảo trước khi preInitLib()
        try {
            val loader = context.classLoader
            loader.loadClass("com.hwa.excelllll.SODoc")
            loader.loadClass("com.hwa.excelllll.SODoc\$SOSelectionContext")
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        preInitLib()
        val locale = Locale.getDefault()
        val language = buildString {
            append(locale.language)
            if (locale.country.isNotBlank()) {
                append('-')
                append(locale.country)
            }
        }

        internal = initLib(language)
        check(internal != 0L) { "SOLib initialisation failed" }

        val tempDir = File(context.cacheDir, "soffice-tmp").apply { mkdirs() }
        check(setTempPath(tempDir.absolutePath + File.separator) == 0) {
            "SOLib failed to set temp directory"
        }
        check(installFonts("/system/fonts/") == 0) {
            "SOLib failed to install system fonts"
        }
    }

    private fun close() {
        if (internal == 0L) return
        finLib()
        internal = 0
    }

    companion object {
        @Volatile
        private var instance: SOLib? = null

        /** Detect if we are running on armeabi-v7a. */
        val isV7a = android.os.Build.SUPPORTED_ABIS[0].startsWith("armeabi-v7a")

        init {
            System.loadLibrary("abc")
            System.loadLibrary("c++_shared")
        }

        @JvmStatic
        @Synchronized
        fun init(context: Context): Int {
            if (instance == null) {
                instance = SOLib().also {
                    it.initialize(context.applicationContext)
                }
            }
            return 0
        }

        @JvmStatic
        @Synchronized
        fun terminate() {
            instance?.close()
            instance = null
        }

        @JvmStatic
        fun requireInstance(): SOLib {
            return checkNotNull(instance) {
                "SOLib is not initialized. Call DocumentEngine.initialize() first."
            }
        }

        @JvmStatic
        fun postToUi(action: Runnable) {
            Handler(Looper.getMainLooper()).post(action)
        }
    }
}
