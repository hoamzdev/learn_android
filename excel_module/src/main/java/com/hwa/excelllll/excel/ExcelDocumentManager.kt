package com.hwa.excelllll.excel

import android.content.Context
import android.net.Uri
import com.hwa.excelllll.SODoc

class ExcelDocumentManager(
    context: Context,
    private val opener: ExcelDocumentLoader.NativeOpener
) {
    private val appContext = context.applicationContext
    private val loader = ExcelDocumentLoader(appContext)

    var state: ExcelDocumentState = ExcelDocumentState.Empty
        private set

    private var cachedPath: String? = null
    private var document: SODoc? = null

    init {
        loader.setNativeOpener(opener)
    }

    fun open(uri: Uri): Result<SODoc> {
        state = ExcelDocumentState.Loading

        return runCatching {
            closeInternal()

            require(ExcelFileAccess.isExcel(appContext, uri)) {
                "Only .xls and .xlsx files are supported."
            }

            cachedPath = ExcelFileAccess.copyToCache(appContext, uri)

            val doc = opener.open(cachedPath!!)
                ?: error("Native Artifex failed to open: $cachedPath")

            document = doc
            state = ExcelDocumentState.Loaded(doc)
            doc
        }.onFailure {
            document = null
            state = ExcelDocumentState.Error(it)
        }
    }

    fun current(): SODoc? = document

    fun close() {
        closeInternal()
        state = ExcelDocumentState.Closed
    }

    private fun closeInternal() {
        document?.let {
            runCatching { it.close() }
        }
        document = null

        cachedPath?.let(ExcelFileAccess::deleteCachedFile)
        cachedPath = null
    }
}
