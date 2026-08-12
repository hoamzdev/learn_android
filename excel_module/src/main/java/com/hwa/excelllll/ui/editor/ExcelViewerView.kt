package com.hwa.excelllll.ui.editor

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.hwa.excelllll.core.DocumentLoader
import com.hwa.excelllll.core.DocumentOpenResult
import com.hwa.excelllll.core.SOBitmap
import com.hwa.excelllll.editor.ExcelBitmapProvider
import com.hwa.excelllll.excel.ExcelDocumentManager
import com.hwa.excelllll.excel.ExcelNativeLoader
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

class ExcelViewerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val progress = ProgressBar(context).apply {
        visibility = GONE
        isIndeterminate = true
    }
    private val message = TextView(context).apply {
        gravity = Gravity.CENTER
        setTextColor(Color.DKGRAY)
        textSize = 16f
        visibility = GONE
        setPadding(10, 10, 10, 10)
    }
    private val worker = Executors.newSingleThreadExecutor()
    private val requestGeneration = AtomicInteger()
    private var pageHost: ExcelPageHost? = null
    private var released = false

    private val documentManager = ExcelDocumentManager(
        context.applicationContext,
        ExcelNativeLoader { path ->
            when (val result = DocumentLoader.open(path)) {
                is DocumentOpenResult.Success -> result.document
                is DocumentOpenResult.Error -> null
            }
        }
    )

    /** Called on the main thread after the first viewport is rendered. */
    var onDocumentReady: (() -> Unit)? = null

    /** Called on the main thread for open and render failures. */
    var onDocumentError: ((String) -> Unit)? = null

    init {
        setBackgroundColor(Color.WHITE)
        addView(
            progress,
            LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
            )
        )
        addView(
            message,
            LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
            )
        )
    }

    fun open(uri: Uri?) {
        check(!released) { "ExcelViewerView has already been closed" }
        if(uri == null){
            return
        }
        if (!isMainThread()) {
            post { open(uri) }
            return
        }

        val generation = requestGeneration.incrementAndGet()
        pageHost?.close()
        pageHost?.let(::removeView)
        pageHost = null
        showLoading()

        worker.execute {
            val result = documentManager.open(uri)
            post {
                if (released || generation != requestGeneration.get()) return@post
                result.fold(
                    onSuccess = { document -> showDocument(document, generation) },
                    onFailure = { error ->
                        showError(error.message ?: "Không mở được file Excel")
                    }
                )
            }
        }
    }

    private fun showDocument(
        document: com.hwa.excelllll.core.SODoc,
        generation: Int
    ) {
        val host = ExcelPageHost(
            context,
            document,
            object : ExcelBitmapProvider {
                override fun create(bitmap: android.graphics.Bitmap): SOBitmap = SOBitmap(bitmap)
            }
        )
        pageHost = host
        addView(
            host,
            0,
            LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        progress.visibility = View.GONE
        message.visibility = View.GONE

        host.post {
            if (released || generation != requestGeneration.get()) return@post
            host.render(0, host.width, host.height) { status ->
                if (released || generation != requestGeneration.get()) return@render
                if (status == 0) {
                    onDocumentReady?.invoke()
                    hideLoading()
                } else {
                    showError("Không render được file (mã lỗi: $status)")
                }
            }
        }
    }

    private fun showLoading() {
        progress.visibility = VISIBLE
        message.visibility = GONE
    }

    private fun hideLoading(){
        progress.visibility = GONE
        message.visibility = GONE
    }


    private fun showError(text: String) {
        progress.visibility = GONE
        message.text = text
        message.visibility = VISIBLE
        onDocumentError?.invoke(text)
    }

    /** Releases the native document and worker. Do not reuse this view afterwards. */
    fun close() {
        if (released) return
        released = true
        requestGeneration.incrementAndGet()
        pageHost?.close()
        pageHost = null
        worker.execute { documentManager.close() }
        worker.shutdown()
    }

    private fun isMainThread(): Boolean =
        android.os.Looper.myLooper() == android.os.Looper.getMainLooper()
}
