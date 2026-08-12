package com.hwa.excelllll.excel

import com.hwa.excelllll.SODoc

sealed interface ExcelDocumentState {
    data object Empty : ExcelDocumentState
    data object Loading : ExcelDocumentState
    data class Loaded(val document: SODoc) : ExcelDocumentState
    data class Error(val throwable: Throwable) : ExcelDocumentState
    data object Closed : ExcelDocumentState
}
