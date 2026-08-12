package com.hwa.excelllll.editor

import com.hwa.excelllll.core.SODoc

class ExcelSearchController(
    private val document: SODoc
) {
    fun setQuery(query: String) = document.setSearchText(query)
    fun setMatchCase(enabled: Boolean) = document.setSearchMatchCase(enabled)
    fun setWholeWord(enabled: Boolean) = document.setSearchWholeWord(enabled)

    fun start(): Int = document.search()
    fun setStart(page: Int, x: Float, y: Float) =
        document.setSearchStart(page, x, y)

    fun cancel() = document.cancelSearch()
    fun close() = document.closeSearch()
    fun isSearching(): Boolean = document.isSearchInProgress()
}
