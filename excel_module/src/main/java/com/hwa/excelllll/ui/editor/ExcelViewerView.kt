package com.hwa.excelllll.ui.editor

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.hwa.excelllll.SODoc
import com.hwa.excelllll.core.DocumentLoader
import com.hwa.excelllll.core.DocumentOpenResult
import com.hwa.excelllll.SOBitmap
import com.hwa.excelllll.editor.ExcelBitmapProvider
import com.hwa.excelllll.editor.ExcelSelectionModel
import com.hwa.excelllll.excel.ExcelDocumentManager
import com.hwa.excelllll.excel.ExcelNativeLoader
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

data class ExcelSelectionState(
    val sheetIndex: Int,
    val text: String,
    val fontSize: Double,
    val firstRow: Int?,
    val firstColumn: Int?,
    val rowCount: Int,
    val columnCount: Int,
    val isMerged: Boolean,
    val canEditText: Boolean,
    val canCopy: Boolean,
    val canDelete: Boolean,
    val isBold : Boolean,
    val isItalic : Boolean,
    val underLine : Boolean,
    val isStrikeThrough : Boolean,
    val canPaste: Boolean
)

class ExcelViewerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val progress = ProgressBar(context).apply {
        visibility = GONE
        isIndeterminate = true
        indeterminateTintList = ColorStateList.valueOf(Color.GREEN)
    }
    private val message = TextView(context).apply {
        gravity = Gravity.CENTER
        setTextColor(Color.DKGRAY)
        textSize = 16f
        visibility = GONE
        clipToPadding = false
        setPadding(10, 10, 10, 0)
    }
    private val worker = Executors.newSingleThreadExecutor()
    private val requestGeneration = AtomicInteger()
    private var pageHost: ExcelPageHost? = null
    private var released = false
    private var currentSheetIndex = 0
    private var editor: ExcelEditorController? = null

    private val documentManager = ExcelDocumentManager(
        context.applicationContext,
        ExcelNativeLoader { path ->
            when (val result = DocumentLoader.open(path)) {
                is DocumentOpenResult.Success -> result.document
                is DocumentOpenResult.Error -> null
            }
        }
    )

    /** Called on the main thread after the first viewport is rendered. */
    var onDocumentReady: ((SODoc) -> Unit)? = null

    /** Called on the main thread for open and render failures. */
    var onDocumentError: ((String) -> Unit)? = null

    /** Preferred callback containing the selected cell/range and edit capabilities. */
    var onCellSelected: ((ExcelSelectionState) -> Unit)? = null

    /** Called after the sheet list changes. */
    var onSheetListChanged: ((List<String>) -> Unit)? = null

    /** Selection helper for enabling/disabling edit controls. */
    val selection: ExcelSelectionModel?
        get() = editor?.selection

    /** Index of the sheet currently displayed by this view. */
    val selectedSheetIndex: Int
        get() = currentSheetIndex

    init {
        setBackgroundColor(Color.WHITE)
        addView(
            progress,
            LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
            )
        )
        addView(
            message,
            LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
            )
        )
    }

    fun open(uri: Uri?) {
        check(!released) { "ExcelViewerView has already been closed" }
        if (uri == null) {
            return
        }
        if (!isMainThread()) {
            post { open(uri) }
            return
        }

        val generation = requestGeneration.incrementAndGet()
        pageHost?.close()
        pageHost?.let(::removeView)
        pageHost = null
        editor = null
        currentSheetIndex = 0
        showLoading()

        worker.execute {
            val result = documentManager.open(uri)
            post {
                if (released || generation != requestGeneration.get()) return@post
                result.fold(
                    onSuccess = { document -> showDocument(document, generation) },
                    onFailure = { error ->
                        showError(error.message ?: "Không mở được file Excel")
                    }
                )
            }
        }
    }

    /** Binds an existing document to this view. Useful for configuration changes. */
    fun bindExistingDocument(document: SODoc, sheetIndex: Int = 0) {
        check(!released) { "ExcelViewerView has already been closed" }
        val generation = requestGeneration.incrementAndGet()
        pageHost?.close()
        pageHost?.let(::removeView)
        
        documentManager.bind(document)
        currentSheetIndex = sheetIndex
        showDocument(document, generation)
        
        // Ensure the correct sheet is rendered
        if (sheetIndex > 0) {
            post { showSheet(sheetIndex) }
        }
    }

    /** Renders the Excel sheet at [index]. Sheet indexes start at 0. */
    fun showSheet(index: Int, onComplete: (Int) -> Unit = {}) {
        check(!released) { "ExcelViewerView has already been closed" }
        if (!isMainThread()) {
            post { showSheet(index, onComplete) }
            return
        }

        val document = documentManager.current()
        if (document == null) {
            onComplete(SHEET_NOT_READY)
            return
        }
        if (index !in 0 until document.sheetCount()) {
            onComplete(SHEET_INDEX_OUT_OF_BOUNDS)
            return
        }

        val host = pageHost
        if (host == null || host.width <= 0 || host.height <= 0) {
            onComplete(SHEET_NOT_READY)
            return
        }

        showLoading()
        host.render(index, host.width, host.height) { status ->
            if (status == 0) {
                currentSheetIndex = index
                hideLoading()
            } else showError("Không render được sheet $index (mã lỗi: $status)")
            onComplete(status)
        }
    }

    private fun showDocument(
        document: SODoc,
        generation: Int
    ) {
        val host = ExcelPageHost(
            context,
            document,
            object : ExcelBitmapProvider {
                override fun create(bitmap: android.graphics.Bitmap): SOBitmap = SOBitmap(bitmap)
            }
        )
        editor = ExcelEditorController(document, context.applicationContext)
        currentSheetIndex = 0
        host.onCellSelected = {
            dispatchSelectionChanged()
        }
        pageHost = host
        addView(
            host,
            0,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
        )
        progress.visibility = GONE
        message.visibility = GONE

        host.post {
            if (released || generation != requestGeneration.get()) return@post
            host.render(0, host.width, host.height) { status ->
                if (released || generation != requestGeneration.get()) return@render
                if (status == 0) {
                    onDocumentReady?.invoke(document)
                    hideLoading()
                } else {
                    showError("Không render được file (mã lỗi: $status)")
                }
            }
        }
    }

    private fun showLoading() {
        progress.visibility = VISIBLE
        message.visibility = GONE
    }

    /** Returns the text of the currently selected cell. Returns empty if a range is selected. */
    fun selectedText(): String {
        val state = selectedCell() ?: return ""
        if (state.rowCount > 1 || state.columnCount > 1) return ""
        return state.text
    }

    /** Returns a snapshot; edits still act on the native selection kept by this view. */
    fun selectedCell(): ExcelSelectionState? {
        val model = editor?.selection ?: return null
        val range = runCatching { model.tableRange() }.getOrNull()
        return ExcelSelectionState(
            sheetIndex = currentSheetIndex,
            text = model.text().orEmpty(),
            fontSize = model.getFontSize(),
            firstRow = range?.let { runCatching { it.firstRow() }.getOrNull() },
            firstColumn = range?.let { runCatching { it.firstColumn() }.getOrNull() },
            rowCount = range?.let { runCatching { it.rowCount() }.getOrDefault(1) } ?: 1,
            columnCount = range?.let { runCatching { it.columnCount() }.getOrDefault(1) } ?: 1,
            isMerged = model.isMerged(),
            canEditText = model.canEditText(),
            canCopy = model.canCopy(),
            canDelete = model.canDelete(),
            canPaste = model.canPaste(),
            isBold = model.isBold(),
            isItalic = model.isItalic(),
            underLine = model.isUnderline(),
            isStrikeThrough = model.isStrikeThrough()
        )
    }

    private fun dispatchSelectionChanged() {
        val state = selectedCell() ?: return
        onCellSelected?.invoke(state)
    }

    fun replaceSelection(value: String): Boolean = editIf(
        allowed = { it.selection.canEditText() },
        action = { it.replaceSelection(value) }
    )

    fun deleteSelection(): Boolean = editIf(
        allowed = { it.selection.canDelete() },
        action = { it.deleteSelection() }
    )

    fun copy(): Boolean {
        val controller = editor ?: return false
        val state = selectedCell() ?: return false
        val isSingleUnit = (state.rowCount == 1 && state.columnCount == 1) || state.isMerged
        if (!isSingleUnit) return false
        if (!controller.selection.canCopy()) return false
        val cleanText = controller.clipboard.getCleanSelectionText()
        if (cleanText.isNotEmpty()) {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = android.content.ClipData.newPlainText("Excel", cleanText)
            clipboard.setPrimaryClip(clip)
            return true
        }
        return false
    }

    fun cut(): Boolean = editIf(
        allowed = { 
            val state = selectedCell() ?: return@editIf false
            val isSingleUnit = (state.rowCount == 1 && state.columnCount == 1) || state.isMerged
            isSingleUnit && it.selection.canCopy() && it.selection.canDelete() 
        },
        action = { controller ->
            // Lấy text sạch của ô (xử lý tốt cho cả ô gộp)
            val cleanText = controller.clipboard.getCleanSelectionText()
            if (cleanText.isNotEmpty()) {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                val clip = android.content.ClipData.newPlainText("Excel", cleanText)
                clipboard.setPrimaryClip(clip)
            }
            // Xóa nội dung ô
            controller.deleteSelection()
        }
    )

    fun paste(): Boolean = editIf(
        allowed = { 
            val state = selectedCell() ?: return@editIf false
            val isSingleUnit = (state.rowCount == 1 && state.columnCount == 1) || state.isMerged
            isSingleUnit && it.selection.canPaste()
        },
        action = { controller ->
            // Ưu tiên lấy dữ liệu từ clipboard hệ thống để đồng bộ với hàm cut() đã sửa
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clipData = clipboard.primaryClip
            if (clipData != null && clipData.itemCount > 0) {
                val text = clipData.getItemAt(0).text?.toString()
                if (!text.isNullOrEmpty()) {
                    controller.replaceSelection(text)
                    return@editIf
                }
            }
            // Nếu clipboard hệ thống trống, dùng lệnh paste mặc định của native
            controller.paste()
        }
    )

    fun setBoldCell(enabled: Boolean): Boolean = edit { it.setBold(enabled) }
    fun setItalicCell(enabled: Boolean): Boolean = edit { it.setItalic(enabled) }
    fun setUnderlineCell(enabled: Boolean): Boolean = edit { it.setUnderline(enabled) }
    fun setStrikeThroughCell(enabled: Boolean): Boolean = edit { it.setStrikeThrough(enabled) }
    fun setFontNameCell(name: String): Boolean = edit { it.setFontName(name) }
    fun setFontSizeCell(size: Double): Boolean = edit { it.setFontSize(size) }
    fun setFontColorCell(color: String): Boolean = edit { it.setFontColor(color) }
    fun setFillColorCell(color: String): Boolean = edit { it.setFillColor(color) }
    fun setBackgroundColorCell(color: String): Boolean = edit { it.setBackgroundColor(color) }
    fun setBackgroundTransparentCell(): Boolean = edit { it.setBackgroundTransparent() }
    fun setAlignmentCell(value: Int): Boolean = edit { it.setAlignment(value) }
    fun setLineColorCell(color: String): Boolean = edit { it.setLineColor(color) }
    fun setCellFormatCell(format: String): Boolean = edit { it.setCellFormat(format) }
    fun setColumnWidthCell(width: Float): Boolean = edit { it.setColumnWidth(width) }
    fun setRowHeightCell(height: Float): Boolean = edit { it.setRowHeight(height) }

    /** Returns whether the native document has an earlier edit to restore. */
    fun canUndo(): Boolean {
        check(!released) { "ExcelViewerView has already been closed" }
        val document = documentManager.current() ?: return false
        return document.getCurrentEdit() > 0
    }

    /** Returns whether the native document has a later edit to restore. */
    fun canRedo(): Boolean {
        check(!released) { "ExcelViewerView has already been closed" }
        val document = documentManager.current() ?: return false
        return document.getCurrentEdit() < document.getNumEdits()
    }

    /** Restores the document state before the most recent edit. */
    fun undo(): Boolean {
        if(canUndo()){
            return moveEditHistory(-1)
        }
        return false
    }

    /** Reapplies the next edit that was previously undone. */
    fun redo(): Boolean{
        if(canRedo()){
            return moveEditHistory(1)
        }
        return false
    }

    fun sheetCount(): Int = documentManager.current()?.sheetCount() ?: 0

    fun sheetNames(): List<String> = documentManager.current()?.sheetNames().orEmpty()

    /** Refuses to delete the only remaining sheet. */
    fun deleteCurrentSheet(): Boolean {
        val count = sheetCount()
        if (count <= 1 || currentSheetIndex !in 0 until count) return false
        val deletedIndex = currentSheetIndex
        val nextIndex = deletedIndex.coerceAtMost(count - 2)
        return mutateSheets(nextIndex) { it.deleteSheet(deletedIndex) }
    }

    fun moveCurrentSheet(toIndex: Int): Boolean {
        return moveSheet(currentSheetIndex, toIndex)
    }

    fun moveSheet(fromIndex: Int, toIndex: Int): Boolean {
        val count = sheetCount()
        if (fromIndex !in 0 until count || toIndex !in 0 until count) return false
        if (toIndex == fromIndex) return true
        val sheetToShow = when (currentSheetIndex) {
            fromIndex -> toIndex
            in (fromIndex + 1)..toIndex -> currentSheetIndex - 1
            in toIndex..<fromIndex -> currentSheetIndex + 1
            else -> currentSheetIndex
        }
        return mutateSheets(sheetToShow) { it.moveSheet(fromIndex, toIndex) }
    }

    private fun mutateSheets(
        sheetToShow: Int,
        action: (ExcelEditorController) -> Unit
    ): Boolean {
        check(!released) { "ExcelViewerView has already been closed" }
        val controller = editor ?: return false
        pageHost?.releaseCurrentPage()
        action(controller)
        currentSheetIndex = sheetToShow
        showSheet(sheetToShow) { status ->
            if (status == 0) onSheetListChanged?.invoke(sheetNames())
        }
        return true
    }

    /**
     * Commits any pending cell edits (like typed text) to the native document.
     * This is asynchronous.
     */
    fun commitCurrentEdit(onComplete: () -> Unit) {
        if (released) {
            onComplete()
            return
        }
        val host = pageHost
        if (host == null) {
            onComplete()
            return
        }
        
        val commitStarted = host.commitAndReselectCell {
            onComplete()
        }
        
        if (!commitStarted) {
            onComplete()
        }
    }

    /** Re-renders the displayed sheet after a successful edit, preserving current scale and scroll position. */
    fun refresh(onComplete: (Int) -> Unit = {}) {
        check(!released) { "ExcelViewerView has already been closed" }
        if (!isMainThread()) {
            post { refresh(onComplete) }
            return
        }

        val host = pageHost
        if (host == null || host.width <= 0 || host.height <= 0) {
            onComplete(SHEET_NOT_READY)
            return
        }

        host.refresh { status ->
            if (status == 0) {
                hideLoading()
            } else {
                showError("Không refresh được sheet $currentSheetIndex (mã lỗi: $status)")
            }
            onComplete(status)
        }
    }

    private fun edit(action: (ExcelEditorController) -> Unit): Boolean =
        editIf(allowed = { true }, action = action)

    private fun moveEditHistory(offset: Int): Boolean {
        check(!released) { "ExcelViewerView has already been closed" }
        val document = documentManager.current() ?: return false
        val targetEdit = document.getCurrentEdit() + offset
        if (targetEdit !in 0..document.getNumEdits()) return false

        document.setCurrentEdit(targetEdit)
        refresh {
            dispatchSelectionChanged()
        }
        return true
    }

    private fun editIf(
        allowed: (ExcelEditorController) -> Boolean,
        action: (ExcelEditorController) -> Unit
    ): Boolean {
        check(!released) { "ExcelViewerView has already been closed" }
        val controller = editor ?: return false
        
        if (!allowed(controller)) return false

        // 1. Thực hiện lệnh Edit ngay lập tức
        action(controller)

        // 2. Ép Native commit dữ liệu ô
        pageHost?.commitAndReselectCell {
            // 3. Quan trọng: Đợi một nhịp ngắn (60ms) để Native cập nhật Display Tree 
            // trước khi thực hiện Refresh màn hình. Việc này sửa lỗi "nhấn 2 lần mới hiện".
            postDelayed({
                refresh()
                dispatchSelectionChanged()
            }, 60L)
        }
        
        return true
    }

    private fun hideLoading() {
        progress.visibility = GONE
        message.visibility = GONE
    }


    private fun showError(text: String) {
        progress.visibility = GONE
        message.text = text
        message.visibility = VISIBLE
        onDocumentError?.invoke(text)
    }

    /** Releases the native document and worker. Do not reuse this view afterwards. */
    fun close() {
        if (released) return
        released = true
        requestGeneration.incrementAndGet()
        pageHost?.close()
        pageHost = null
        worker.execute {
            documentManager.close()
            editor?.close()
        }
        editor = null
        worker.shutdown()
    }

    private fun isMainThread(): Boolean =
        android.os.Looper.myLooper() == android.os.Looper.getMainLooper()

    companion object {
        const val SHEET_NOT_READY = -2
        const val SHEET_INDEX_OUT_OF_BOUNDS = -3
    }
}
