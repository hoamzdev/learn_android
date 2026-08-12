package com.hwa.excelllll.ui.editor

import android.content.Context
import com.hwa.excelllll.SODoc
import com.hwa.excelllll.editor.ExcelBitmapProvider

object ExcelPageHostFactory {

    fun create(
        context: Context,
        document: SODoc,
        bitmapProvider: ExcelBitmapProvider
    ): ExcelPageHost {
        return ExcelPageHost(
            context = context,
            document = document,
            bitmapProvider = bitmapProvider
        )
    }
}
