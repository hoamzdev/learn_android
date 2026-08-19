package com.hwa.excelllll.core

import com.hwa.excelllll.SODoc
import com.hwa.excelllll.SODocLoadListenerInternal
import com.hwa.excelllll.SOLib
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference

/**
 * Thin loader facade. The exact native open routine differs between Artifex
 * builds, so keep the native entry point isolated here.
 *
 * If the supplied .so exposes a different JNI method, only this class needs
 * to be adjusted.
 */
object DocumentLoader {

    @Volatile
    private var activeListener: SODocLoadListenerInternal? = null

    fun open(path: String, password: String? = null): DocumentOpenResult {
        return try {
            val ready = CountDownLatch(1)
            val callbackError = AtomicReference<DocumentOpenResult.Error?>()
            val listener = object : SODocLoadListenerInternal {
                    override fun a(doc: SODoc) = Unit

                    override fun error(code: Int, detail: Int) {
                        callbackError.set(DocumentOpenResult.Error(
                            code,
                            "Native open error: code=$code, detail=$detail"
                        ))
                        activeListener = null
                        ready.countDown()
                    }

                    override fun onLayoutCompleted() {
                        ready.countDown()
                    }

                    override fun onPageSizeChanged() = Unit

                    override fun onSelectionChanged(start: Int, end: Int) = Unit
                    override fun progress(progress: Int, finished: Boolean) {
                        // Loading the file bytes is not the same as completing
                        // document layout. getPage() is only safe after
                        // onLayoutCompleted().
                    }
                }
            activeListener = listener
            val document = SOLib.requireInstance()
                .openDocumentInternal(path, listener)

            if (document == null) {
                activeListener = null
                return callbackError.get()
                    ?: DocumentOpenResult.Error(-1, "Native document is null")
            }

            val loaded = ready.await(30, TimeUnit.SECONDS)
            activeListener = null

            when {
                callbackError.get() != null -> {
                    runCatching { document.close() }
                    callbackError.get()!!
                }
                loaded -> {
                    if (!password.isNullOrEmpty()) {
                        document.providePassword(password)
                    }
                    DocumentOpenResult.Success(document)
                }
                else -> {
                    runCatching { document.close() }
                    DocumentOpenResult.Error(
                        -1,
                        "Timed out waiting for native document layout"
                    )
                }
            }
        } catch (t: Throwable) {
            activeListener = null
            DocumentOpenResult.Error(-1, t.message)
        }
    }
}
