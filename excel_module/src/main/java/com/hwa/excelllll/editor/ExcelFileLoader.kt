package com.hwa.excelllll.editor

import android.content.Context
import android.net.Uri
import com.hwa.excelllll.core.SODoc

class ExcelFileLoader(
    private val nativeLoader: NativeDocumentLoader
) {
    fun open(context: Context, uri: Uri): SODoc {
        require(uri != Uri.EMPTY) { "Invalid document Uri" }
        return nativeLoader.open(context, uri)
    }
}
