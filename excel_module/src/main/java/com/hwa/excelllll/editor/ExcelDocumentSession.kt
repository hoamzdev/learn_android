package com.hwa.excelllll.editor

import com.hwa.excelllll.SODoc

class ExcelDocumentSession(
    document: SODoc
) {
    private val lifecycle = ExcelDocumentLifecycle(document)

    var state: ExcelDocumentState = ExcelDocumentState.READY
        private set

    val doc: SODoc
        get() = lifecycle.get()
            ?: error("Document is closed")

    fun markModified() {
        if (state != ExcelDocumentState.CLOSED) {
            state = ExcelDocumentState.MODIFIED
        }
    }

    fun save(path: String, onComplete: (Int, Int) -> Unit) {
        state = ExcelDocumentState.SAVING

        lifecycle.save(path) { result, extra ->
            state = if (result == 0) {
                ExcelDocumentState.READY
            } else {
                ExcelDocumentState.ERROR
            }
            onComplete(result, extra)
        }
    }

    fun close() {
        lifecycle.close()
        state = ExcelDocumentState.CLOSED
    }
}
