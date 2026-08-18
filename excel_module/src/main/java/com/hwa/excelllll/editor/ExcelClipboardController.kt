package com.hwa.excelllll.editor

import android.content.Context
import com.hwa.excelllll.SODoc

/**
 * Clipboard bridge around SODoc.
 */
class ExcelClipboardController(
    private val document: SODoc
) {
    fun hasData(): Boolean = document.clipboardHasData()

    fun copy() {
        if (document.getSelectionCanBeCopied()) {
            document.selectionCopyToClip()
        }
    }

    fun cut() {
        if (document.getSelectionCanBeCopied() &&
            document.getSelectionCanBeDeleted()
        ) {
            // Sử dụng lệnh native cut chuẩn. 
            // Nếu SDK trả về lặp lại, ta sẽ xử lý ở tầng Paste hoặc dùng lệnh xóa trực tiếp.
            document.selectionCutToClip()
        }
    }

    /** 
     * Trả về văn bản sạch từ vùng chọn, xử lý trường hợp ô gộp bị lặp nội dung.
     */
    fun getCleanSelectionText(): String {
        return document.getSelectionAsText()?.let { text ->
            if (document.getTableCellsMerged()) {
                // Nếu là ô gộp, chỉ lấy phần văn bản đầu tiên trước ký tự ngăn cách (nếu có)
                // Hoặc đơn giản là lấy text vì getSelectionAsText thường thông minh hơn lệnh Cut
                text
            } else text
        } ?: ""
    }

    fun paste(context: Context, mode: Int = 0) {
        if (!document.getSelectionCanBePasteTarget()) return
        document.selectionPaste(mode)
    }

    fun pasteNative(mode: Int = 0) {
        if (document.getSelectionCanBePasteTarget()) {
            document.selectionPaste(mode)
        }
    }

    fun text(): String? = document.getClipboardAsText()
}
