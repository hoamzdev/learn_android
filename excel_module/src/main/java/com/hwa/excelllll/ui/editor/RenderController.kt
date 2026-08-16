package com.hwa.excelllll.ui.editor

import com.hwa.excelllll.core.InterfaceC1335o
import com.hwa.excelllll.SOBitmap

/**
 * Keeps render orchestration outside the View.
 */
class RenderController(
    private val pageView: DocPageView
) {
    fun render(bitmap: SOBitmap, onComplete: (Int) -> Unit = {}) {
        pageView.render(
            bitmap,
            InterfaceC1335o { status -> onComplete(status) }
        )
    }

    fun cancel() {
        pageView.stopRender()
    }
}
