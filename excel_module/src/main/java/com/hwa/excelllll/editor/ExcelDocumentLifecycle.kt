package com.hwa.excelllll.editor

import com.hwa.excelllll.core.SODoc
import com.hwa.excelllll.core.SODocSaveListener

class ExcelDocumentLifecycle(
    private var document: SODoc?
) {
    fun get(): SODoc? = document

    fun isModified(): Boolean =
        document?.getHasBeenModified() == true

    fun save(path: String, listener: SODocSaveListener) {
        document?.save(path, listener)
    }

    fun savePdf(path: String, flatten: Boolean, listener: SODocSaveListener) {
        document?.savePdf(path, flatten, listener)
    }

    fun close() {
        document?.close()
        document = null
    }

    fun replace(newDocument: SODoc?) {
        document?.close()
        document = newDocument
    }
}
