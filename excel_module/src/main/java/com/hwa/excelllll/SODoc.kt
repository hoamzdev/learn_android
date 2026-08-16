package com.hwa.excelllll

import android.content.Context
import android.graphics.PointF
import android.graphics.RectF

/**
 * JNI-backed document object.
 *
 * Only the native boundary is kept here. Application/UI code should use the
 * readable methods below instead of decompiled m56xx names.
 */
open class SODoc(
    @JvmField protected var internal: Long
) {
    private var searchText: String = ""
    private var searchBackwards: Boolean = false
    private var searchMatchCase: Boolean = false
    private var searchWholeWord: Boolean = false
    private var searchInProgress: Boolean = false

    /**
     * Called directly by libso.so during a native search.
     * Keep this exact JVM signature: searchProgress(IIFFFF)V.
     */
    @Suppress("unused")
    private fun searchProgress(
        page: Int,
        box: Int,
        left: Float,
        top: Float,
        right: Float,
        bottom: Float
    ) {
        // Search UI is not wired into the viewer yet. The callback must still
        // exist because preInitLib registers it unconditionally.
        searchInProgress = false
    }

    external fun destroy()

    external fun abortLoad()
    external fun acceptTrackedChange()
    external fun addBlankPage(index: Int)
    external fun addColumnsLeft()
    external fun addColumnsRight()
    external fun addHighlightAnnotation()
    external fun addRowsAbove()
    external fun addRowsBelow()
    external fun cancelSearch()
    external fun clearSelection()
    external fun clipboardHasData(): Boolean
    external fun deleteChar()
    external fun deleteColumns()
    external fun deleteHighlightAnnotation()
    external fun deletePage(index: Int)
    external fun deleteRows()
    external fun duplicatePage(index: Int)
    external fun getAuthor(): String?
    external fun getBgColorList(): Array<String>?
    external fun getClipboardAsText(): String?
    external fun getCurrentEdit(): Int
    external fun getFontList(): String?
    external fun getHasBeenModified(): Boolean
    external fun getIndentationLevel(): IntArray?
    external fun getNumEdits(): Int
    external fun getNumPagesInternal(): Int
    external fun getPage(index: Int, listener: SOPageListener?): SOPage?
    external fun getSelectedCellFormat(): String?
    external fun getSelectedColumnWidth(): Float
    external fun getSelectedRowHeight(): Float
    external fun getSelectionAlignment(): Int
    external fun getSelectionAlignmentV(): Int
    external fun getSelectionAsText(): String?
    external fun getSelectionCanBeCopied(): Boolean
    external fun getSelectionCanBeDeleted(): Boolean
    external fun getSelectionCanBePasteTarget(): Boolean
    external fun getSelectionCanBeResized(): Boolean
    external fun getSelectionCanBeRotated(): Boolean
    external fun getSelectionCanHaveTextAltered(): Boolean
    external fun getSelectionFontName(): String?
    external fun getSelectionFontSize(): Double
    external fun getSelectionIsBold(): Boolean
    external fun getSelectionIsItalic(): Boolean
    external fun getSelectionIsLinethrough(): Boolean
    external fun getSelectionIsUnderlined(): Boolean
    external fun getSelectionLineType(): Int
    external fun getSelectionLineWidth(): Float
    external fun getSelectionListStyleIsDecimal(): Boolean
    external fun getSelectionListStyleIsDisc(): Boolean
    external fun getSelectionRotation(): Float
    external fun getShowingTrackedChanges(): Boolean
    external fun getTableCellsMerged(): Boolean
    external fun getTrackingChanges(): Boolean
    external fun movePage(from: Int, to: Int)
    external fun nextTrackedChange(): Boolean
    external fun previousTrackedChange(): Boolean
    external fun processKeyCommand(command: Int)
    external fun providePassword(password: String): Boolean
    external fun rejectTrackedChange()
    external fun selectionCopyToClip()
    external fun selectionCutToClip()
    external fun selectionDelete()
    external fun selectionIsAutoshapeOrImage(): Boolean
    external fun selectionIsReviewable(): Boolean
    external fun selectionPaste(mode: Int)
    external fun selectionTableRange(): SOSelectionTableRange?
    external fun setAuthor(author: String): Boolean
    external fun setCurrentEdit(edit: Int)
    external fun setFlowModeInternal(mode: Int, width: Float)
    external fun setIndentationLevel(level: Int)
    external fun setSelectedCellFormat(format: String)
    external fun setSelectedColumnWidth(width: Float)
    external fun setSelectedRowHeight(height: Float)
    external fun setSelectionAlignment(alignment: Int)
    external fun setSelectionAlignmentV(alignment: Int)
    external fun setSelectionAnnotationComment(comment: String)
    external fun setSelectionArrangeBack()
    external fun setSelectionArrangeBackwards()
    external fun setSelectionArrangeForwards()
    external fun setSelectionArrangeFront()
    external fun setSelectionBackgroundColor(color: String)
    external fun setSelectionBackgroundTransparent()
    external fun setSelectionFillColor(color: String)
    external fun setSelectionFontColor(color: String)
    external fun setSelectionFontName(name: String)
    external fun setSelectionFontSize(size: Double)
    external fun setSelectionIsBold(value: Boolean)
    external fun setSelectionIsItalic(value: Boolean)
    external fun setSelectionIsLinethrough(value: Boolean)
    external fun setSelectionIsUnderlined(value: Boolean)
    external fun setSelectionLineColor(color: String)
    external fun setSelectionLineType(type: Int)
    external fun setSelectionLineWidth(width: Float)
    external fun setSelectionRotation(rotation: Float)
    external fun setSelectionText(text: String, mode: Int, flag: Boolean)
    external fun setShowingTrackedChanges(value: Boolean)
    external fun setTableCellsMerged(value: Boolean)
    external fun setTrackingChanges(value: Boolean)

    external fun getSelectionNaturalDimensionsInternal(): String?
    external fun saveToInternal(path: String, listener: SODocSaveListener): Int
    external fun saveToPDFInternal(path: String, flag: Boolean, listener: SODocSaveListener): Int
    private external fun nativeSearch(text: String, backwards: Boolean, matchCase: Boolean): Int
    private external fun nativeSetSearchStart(page: Int, x: Float, y: Float)
    private external fun nativeCloseSearch()

    /** For Excel documents, every native page represents one sheet. */
    open fun pageCount(): Int = getNumPagesInternal().coerceAtLeast(0)

    fun sheetCount(): Int = pageCount()

    fun sheetName(index: Int): String? {
        if (index !in 0 until sheetCount()) return null
        // Artifex initializes page metadata (including the Excel tab title)
        // through the getPage() path that has an SOPageListener. Passing null
        // can return a page handle whose title has not been populated yet.
        val page = getPage(
            index,
            object : SOPageListener {
                override fun update(rect: RectF) = Unit
            }
        ) ?: return null
        return try {
            page.getPageTitle()?.takeIf { it.isNotBlank() }
        } finally {
            runCatching { page.discard() }
        }
    }

    fun sheetNames(): List<String> = List(sheetCount()) { index ->
        sheetName(index) ?: "Sheet ${index + 1}"
    }

    fun selectionNaturalSize(): PointF {
        val raw = getSelectionNaturalDimensionsInternal() ?: return PointF()
        val parts = raw.split(',')
        if (parts.size != 2) return PointF()
        return runCatching {
            PointF(parts[0].toFloat(), parts[1].toFloat())
        }.getOrDefault(PointF())
    }

    fun save(path: String, listener: SODocSaveListener) {
        saveToInternal(path, listener)
    }

    fun savePdf(path: String, flag: Boolean, listener: SODocSaveListener) {
        saveToPDFInternal(path, flag, listener)
    }

    fun close() {
        destroy()
    }

    fun setSearchText(value: String) {
        check(!searchInProgress) { "Search is already in progress" }
        searchText = value
    }

    fun setSearchBackwards(value: Boolean) {
        check(!searchInProgress) { "Search is already in progress" }
        searchBackwards = value
    }

    fun setSearchMatchCase(value: Boolean) {
        check(!searchInProgress) { "Search is already in progress" }
        searchMatchCase = value
    }

    fun setSearchWholeWord(value: Boolean) {
        check(!searchInProgress) { "Search is already in progress" }
        searchWholeWord = value
    }

    fun search(): Int {
        if (searchText.isBlank() || searchInProgress) return 0
        searchInProgress = true
        return runCatching {
            nativeSearch(searchText, searchBackwards, searchMatchCase)
        }.onFailure {
            searchInProgress = false
        }.getOrThrow()
    }

    fun setSearchStart(page: Int, x: Float, y: Float) {
        if (!searchInProgress) nativeSetSearchStart(page, x, y)
    }

    fun closeSearch() {
        nativeCloseSearch()
        searchInProgress = false
    }

    fun isSearchInProgress(): Boolean = searchInProgress

    protected fun copy(context: Context) {
        if (getSelectionCanBeCopied()) {
            selectionCopyToClip()
        }
    }
}