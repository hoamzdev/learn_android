package com.hwa.excelllll.core

/**
 * Small facade containing only operations needed by an Excel viewer/editor.
 */
class ExcelDocument(
    val document: SODoc
) {
    val pageCount: Int
        get() = document.pageCount()

    fun page(index: Int): SOPage? =
        document.getPage(index, null)

    fun save(path: String, listener: SODocSaveListener) =
        document.save(path, listener)

    fun savePdf(path: String, includeAnnotations: Boolean, listener: SODocSaveListener) =
        document.savePdf(path, includeAnnotations, listener)

    fun close() {
        document.close()
    }
}
