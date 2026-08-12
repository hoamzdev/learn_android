package com.hwa.excelllll.editor

import com.hwa.excelllll.core.SODoc

/**
 * Keeps the UI independent from the concrete SODoc implementation.
 */
class ArtifexExcelBridge(
    private val document: SODoc
) {
    fun pageCount(): Int {
        // Artifex's SODoc wrapper supplied by the project should expose
        // the native page-count API here.
        return document.pageCount()
    }

    fun page(index: Int) = document.getPage(index, null)

    fun modified(): Boolean = document.getHasBeenModified()

    fun close() = document.close()
}
