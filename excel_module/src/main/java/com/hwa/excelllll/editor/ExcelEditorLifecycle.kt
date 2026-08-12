package com.hwa.excelllll.editor

import com.hwa.excelllll.core.SODoc

/**
 * Owns the application-level lifecycle of one SODoc.
 *
 * Activity/Fragment should call close() when the document screen is finished.
 */
class ExcelEditorLifecycle {

    private var document: SODoc? = null

    fun attach(value: SODoc): ExcelEditor {
        close()
        document = value
        return ExcelEditor(value)
    }

    fun current(): SODoc? = document

    fun close() {
        val value = document ?: return
        try {
            value.destroy()
        } catch (_: Throwable) {
        }
        document = null
    }
}
