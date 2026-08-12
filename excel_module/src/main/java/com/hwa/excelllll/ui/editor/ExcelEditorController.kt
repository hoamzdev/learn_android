package com.hwa.excelllll.ui.editor

import android.content.Context
import com.hwa.excelllll.SODoc
import com.hwa.excelllll.editor.ExcelCellEditor
import com.hwa.excelllll.editor.ExcelClipboardController
import com.hwa.excelllll.editor.ExcelDocumentSession
import com.hwa.excelllll.editor.ExcelFormattingController
import com.hwa.excelllll.editor.ExcelSearchController
import com.hwa.excelllll.editor.ExcelSelectionModel

/**
 * Single entry point used by the Excel screen.
 *
 * UI should not need to know the obfuscated Artifex method names.
 */
class ExcelEditorController(
    private val document: SODoc,
    context: Context? = null
) {
    val session = ExcelDocumentSession(document)
    val selection = ExcelSelectionModel(document)
    val cells = ExcelCellEditor(document)
    val formatting = ExcelFormattingController(document)
    val clipboard = ExcelClipboardController(document)
    val search = ExcelSearchController(document)

    private val appContext = context

    fun replaceSelection(value: String) {
        cells.replaceSelection(value)
        session.markModified()
    }

    fun deleteSelection() {
        cells.clearSelection()
        session.markModified()
    }

    fun copy() = clipboard.copy()
    fun cut() {
        clipboard.cut()
        session.markModified()
    }

    fun paste() {
        if (appContext != null) clipboard.paste(appContext)
        else clipboard.pasteNative()
        session.markModified()
    }

    fun insertRowAbove() {
        cells.insertRowAbove()
        session.markModified()
    }

    fun insertRowBelow() {
        cells.insertRowBelow()
        session.markModified()
    }

    fun insertColumnLeft() {
        cells.insertColumnLeft()
        session.markModified()
    }

    fun insertColumnRight() {
        cells.insertColumnRight()
        session.markModified()
    }

    fun deleteRows() {
        cells.deleteRows()
        session.markModified()
    }

    fun deleteColumns() {
        cells.deleteColumns()
        session.markModified()
    }

    fun setBold(enabled: Boolean) {
        formatting.bold(enabled)
        session.markModified()
    }

    fun setItalic(enabled: Boolean) {
        formatting.italic(enabled)
        session.markModified()
    }

    fun setUnderline(enabled: Boolean) {
        formatting.underline(enabled)
        session.markModified()
    }

    fun setStrikeThrough(enabled: Boolean) {
        formatting.strikeThrough(enabled)
        session.markModified()
    }

    fun setFontSize(size: Double) {
        formatting.fontSize(size)
        session.markModified()
    }

    fun setFontName(name: String) {
        formatting.fontName(name)
        session.markModified()
    }

    fun setFontColor(color: String) {
        formatting.fontColor(color)
        session.markModified()
    }

    fun setFillColor(color: String) {
        formatting.fillColor(color)
        session.markModified()
    }

    fun setBackgroundColor(color: String) {
        formatting.backgroundColor(color)
        session.markModified()
    }

    fun setBackgroundTransparent() {
        formatting.backgroundTransparent()
        session.markModified()
    }

    fun setAlignment(value: Int) {
        formatting.alignment(value)
        session.markModified()
    }

    fun setVerticalAlignment(value: Int) {
        formatting.verticalAlignment(value)
        session.markModified()
    }

    fun setRotation(degrees: Float) {
        formatting.rotation(degrees)
        session.markModified()
    }

    fun setLineColor(color: String) {
        formatting.lineColor(color)
        session.markModified()
    }

    fun setLineType(type: Int) {
        formatting.lineType(type)
        session.markModified()
    }

    fun setLineWidth(width: Float) {
        formatting.lineWidth(width)
        session.markModified()
    }

    fun setCellFormat(format: String) {
        document.setSelectedCellFormat(format)
        session.markModified()
    }

    fun setColumnWidth(width: Float) {
        document.setSelectedColumnWidth(width)
        session.markModified()
    }

    fun setRowHeight(height: Float) {
        document.setSelectedRowHeight(height)
        session.markModified()
    }

    fun setIndentationLevel(level: Int) {
        document.setIndentationLevel(level)
        session.markModified()
    }

    fun addSheet(index: Int) {
        document.addBlankPage(index)
        session.markModified()
    }

    fun duplicateSheet(index: Int) {
        document.duplicatePage(index)
        session.markModified()
    }

    fun deleteSheet(index: Int) {
        document.deletePage(index)
        session.markModified()
    }

    fun moveSheet(from: Int, to: Int) {
        document.movePage(from, to)
        session.markModified()
    }

    fun mergeCells(enabled: Boolean) {
        cells.mergeCells(enabled)
        session.markModified()
    }

    fun save(path: String, callback: (Int, Int) -> Unit) {
        session.save(path, callback)
    }

    fun close() {
        session.close()
    }
}
