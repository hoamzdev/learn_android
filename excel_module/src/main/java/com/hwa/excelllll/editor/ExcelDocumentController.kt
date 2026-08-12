package com.hwa.excelllll.editor

import com.hwa.excelllll.core.SODoc
import com.hwa.excelllll.core.SOPage
import com.hwa.excelllll.core.SOPageListener
import com.hwa.excelllll.core.SODocSaveListener

class ExcelDocumentController(
    val document: SODoc
) {
    val selection = ExcelSelectionController(document)
    val edit = ExcelEditController(document)
    val formatting = ExcelFormattingController(document)
    val rowsColumns = ExcelRowColumnController(document)
    val clipboard = ExcelClipboardController(document)

    fun page(index: Int, listener: SOPageListener? = null): SOPage? =
        document.getPage(index, listener)

    fun modified(): Boolean =
        document.getHasBeenModified()

    fun currentEdit(): Int =
        document.getCurrentEdit()

    fun save(path: String, listener: SODocSaveListener) {
        document.save(path, listener)
    }

    fun savePdf(
        path: String,
        includeAnnotations: Boolean,
        listener: SODocSaveListener
    ) {
        document.savePdf(path, includeAnnotations, listener)
    }

    fun close() {
        document.destroy()
    }
}
