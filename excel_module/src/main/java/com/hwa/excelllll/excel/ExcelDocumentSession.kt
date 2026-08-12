package com.hwa.excelllll.excel

import android.content.Context
import android.net.Uri
import com.hwa.excelllll.core.SODoc

/**
 * Owns the lifetime of one Excel document.
 *
 * Keep native ownership in one place so Activity/Fragment/View code never
 * calls native lifecycle methods directly.
 */
class ExcelDocumentSession(
    private val context: Context
) {
    private var document: SODoc? = null
    private var closed = false

    fun attach(nativeDoc: SODoc) {
        check(!closed) { "Document session is already closed." }
        releaseDocument()
        document = nativeDoc
    }

    fun getDocument(): SODoc? = document

    fun requireDocument(): SODoc =
        document ?: error("No Excel document is attached.")

    fun loadFromUri(uri: Uri): SODoc {
        check(!closed) { "Document session is already closed." }

        /*
         * The actual native open/load entry point depends on the .so bridge
         * supplied by the original Artifex build. Keep this class independent
         * from a guessed constructor/API; the caller should create SODoc from
         * the real native loader and call attach().
         */
        error("Create SODoc through the native loader, then call attach(). URI=$uri")
    }

    fun close() {
        if (closed) return
        closed = true
        releaseDocument()
    }

    private fun releaseDocument() {
        document?.let {
            try {
                it.close()
            } catch (_: Throwable) {
                // Native cleanup must not crash Activity teardown.
            }
        }
        document = null
    }
}
