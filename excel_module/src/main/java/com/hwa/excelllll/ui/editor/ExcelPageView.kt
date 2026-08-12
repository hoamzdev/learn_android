package com.hwa.excelllll.ui.editor

import android.annotation.SuppressLint
import android.content.Context
import com.hwa.excelllll.core.InterfaceC1335o
import com.hwa.excelllll.SOBitmap
import com.hwa.excelllll.SODoc
import com.hwa.excelllll.ui.editor.DocPageView

/**
 * Minimal Android page view.
 *
 * It deliberately keeps the native SOPage/SOBitmap/SORender contract intact.
 */
@SuppressLint("ViewConstructor")
class ExcelPageView(
    context: Context,
    document: SODoc
) : DocPageView(context, document) {

    fun bindPage(index: Int) {
        changePage(index)
    }

    fun renderPage(input: SOBitmap, scale: Double = 1.0) {
        render(input, InterfaceC1335o { code ->
            if (code == 0) postInvalidate()
        })
    }

    fun releasePage() = dropPage()

    fun pageIndex(): Int = getPageNumber()
}
