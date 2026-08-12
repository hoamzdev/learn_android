package com.hwa.excelllll.editor

import android.content.Context
import com.hwa.excelllll.SODoc

class ExcelSearchAndEditFacade(
    document: SODoc,
    context: Context? = null
) {
    val search = ExcelSearchController(document)
    val edit = ExcelCellEditor(document)
    val selection = ExcelSelectionModel(document)
    val formatting = ExcelFormattingController(document)
    val clipboard = ExcelClipboardController(document)

    val context: Context? = context
}
