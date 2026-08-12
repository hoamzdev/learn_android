package com.hwa.excelllll.excel

/**
 * Small lifecycle facade for Activity/Fragment.
 *
 * The UI should call these methods instead of reaching into SODoc/SORender
 * directly.
 */
class ExcelEditorLifecycle(
    private val session: ExcelDocumentSession,
    private val renderer: ExcelRenderController,
    private val pages: ExcelPageController
) {
    fun onStart() {
        // Reserved for future native/document activation.
    }

    fun onStop() {
        renderer.stop()
    }

    fun onDestroy() {
        renderer.release()
        pages.release()
        session.close()
    }
}
