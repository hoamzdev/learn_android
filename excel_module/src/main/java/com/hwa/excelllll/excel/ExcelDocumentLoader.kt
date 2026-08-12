package com.hwa.excelllll.excel

import android.content.Context
import android.net.Uri
import com.hwa.excelllll.SODoc

/**
 * Boundary between Android file access and the native Artifex document.
 *
 * Do not invent a native open signature here. The concrete native loader
 * must be wired to the loader that exists in the supplied .so/JNI layer.
 */
class ExcelDocumentLoader(
    private val context: Context
) {
    interface NativeOpener {
        fun open(path: String): SODoc?
    }

    private var nativeOpener: NativeOpener? = null

    fun setNativeOpener(opener: NativeOpener) {
        nativeOpener = opener
    }

    fun open(uri: Uri): SODoc {
        val path = ExcelFileAccess.copyToCache(context, uri)
        return nativeOpener?.open(path)
            ?: error("NativeOpener is not configured.")
    }
}
