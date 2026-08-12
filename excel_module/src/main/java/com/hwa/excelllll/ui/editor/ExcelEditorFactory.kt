package com.hwa.excelllll.ui.editor

import android.content.Context
import com.hwa.excelllll.core.SODoc

object ExcelEditorFactory {
    fun create(context: Context, document: SODoc): ExcelEditorView {
        return ExcelEditorView(context).apply {
            bind(document)
        }
    }
}
