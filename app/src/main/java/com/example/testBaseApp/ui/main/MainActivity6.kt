package com.example.testBaseApp.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Base64
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.example.testBaseApp.base.BaseActivity
import com.example.testBaseApp.databinding.ActivityMain6Binding
import com.example.testBaseApp.ui.StreamingExcelReader
import com.example.testBaseApp.ui.adapter.TabSheetAdapter
import com.example.testBaseApp.utils.gone
import com.example.testBaseApp.utils.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.math.max

class MainActivity6 :
    BaseActivity<ActivityMain6Binding>(ActivityMain6Binding::inflate) {

    companion object {
        /*
         * DOM chỉ giữ khoảng 220–250 row.
         */
        private const val DOM_WINDOW_ROWS = 220

        /*
         * Khi còn cách đầu/cuối window ~70 row thì preload.
         */
        private const val PRELOAD_ROWS = 70

        private const val ROW_HEIGHT = 32

        private const val REQUEST_THROTTLE_MS = 100L

        private const val MIN_DOM_ROWS = 180
    }

    // ============================================================
    // URI
    // ============================================================

    private val uri: Uri? by lazy {
        intent.getStringExtra(MainActivity5.KEY_JSON_FILE)?.toUri()
    }

    // ============================================================
    // TAB
    // ============================================================

    private val adapter by lazy { TabSheetAdapter() }
    private var currentTab = 0

    // ============================================================
    // EXCEL
    // ============================================================

    private var reader: StreamingExcelReader? = null
    private var tempFile: File? = null
    private var metadata: StreamingExcelReader.SheetMetadata? = null

    // ============================================================
    // CURRENT WINDOW
    // ============================================================

    private var currentWindowStart = 0
    private var currentWindowEnd = 0

    // ============================================================
    // LOAD JOB
    // ============================================================

    private var loadJob: Job? = null

    // ============================================================
    // PENDING REQUEST
    // ============================================================

    private data class WindowRequest(
        val start: Int,
        val end: Int,
        val anchorRow: Int,
        val direction: Int
    )

    private var pendingRequest: WindowRequest? = null

    // ============================================================
    // WINDOW CACHE (giảm giật)
    // ============================================================

    private val windowCache = object : LinkedHashMap<Pair<Int, Int>, String>(16, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<Pair<Int, Int>, String>?): Boolean {
            return size > 10
        }
    }

    // ============================================================
    // MERGE CACHE
    // ============================================================

    private var mergeMap: Map<Pair<Int, Int>, StreamingExcelReader.MergeRegion> = emptyMap()

    override val belowStatusBar: Boolean
        get() = false

    // ============================================================
    // INIT
    // ============================================================

    override fun initView() {
        setUpWebView()
        onSelectTab()

        uri?.let { loadExcelFromUri(it) }

        viewBinding.tvNameFile.text = getNameFileFromUri(this, uri)
    }

    // ============================================================
    // WEBVIEW
    // ============================================================

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() {
        viewBinding.webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            builtInZoomControls = true
            displayZoomControls = false
            setSupportZoom(true)
            textZoom = 100

            // Cho phép zoom out tốt hơn
            useWideViewPort = true
            loadWithOverviewMode = false

            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            allowFileAccess = false
            allowContentAccess = false
            cacheMode = WebSettings.LOAD_NO_CACHE
        }

        viewBinding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                view?.evaluateJavascript(
                    """
                    if (window.checkVirtualScroll) {
                        window.checkVirtualScroll();
                    }
                    """.trimIndent(),
                    null
                )
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url?.toString()
                if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
                        return true
                    } catch (_: Exception) {
                    }
                }
                return false
            }
        }

        viewBinding.webView.addJavascriptInterface(AndroidBridge(), "Android")
    }

    // ============================================================
    // JS BRIDGE
    // ============================================================

    private inner class AndroidBridge {
        @JavascriptInterface
        fun requestWindow(
            start: Int,
            end: Int,
            anchorRow: Int,
            direction: Int
        ) {
            runOnUiThread {
                enqueueWindowRequest(
                    start = start,
                    end = end,
                    anchorRow = anchorRow,
                    direction = direction
                )
            }
        }
    }

    // ============================================================
    // ENQUEUE REQUEST
    // ============================================================

    private fun enqueueWindowRequest(
        start: Int,
        end: Int,
        anchorRow: Int,
        direction: Int
    ) {
        val info = metadata ?: return
        val maxRow = info.maxRow
        if (maxRow <= 0) return

        val safeStart = start.coerceIn(0, maxRow - 1)
        val safeEnd = end.coerceIn(safeStart + 1, maxRow)

        if (safeStart == currentWindowStart && safeEnd == currentWindowEnd) {
            resetJsLoading()
            return
        }

        val request = WindowRequest(
            start = safeStart,
            end = safeEnd,
            anchorRow = anchorRow.coerceIn(safeStart, safeEnd - 1),
            direction = direction
        )

        if (loadJob?.isActive == true) {
            pendingRequest = request
            return
        }

        loadWindow(request)
    }

    // ============================================================
    // COPY FILE
    // ============================================================

    private fun loadExcelFromUri(uri: Uri) {
        viewBinding.progressBar.visible()

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val originalName = getNameFileFromUri(this@MainActivity6, uri)
                val extension = originalName
                    .substringAfterLast('.', "xlsx")
                    .lowercase()

                if (extension != "xls" && extension != "xlsx") {
                    throw IllegalArgumentException("Unsupported Excel format: .$extension")
                }

                val file = File(
                    cacheDir,
                    "stream_excel_${System.currentTimeMillis()}.$extension"
                )

                contentResolver.openInputStream(uri)?.use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output, 64 * 1024)
                    }
                } ?: throw IllegalStateException("Cannot open file")

                tempFile = file

                val newReader = StreamingExcelReader(file)
                reader = newReader

                val sheets = newReader.getSheets()

                withContext(Dispatchers.Main) {
                    if (sheets.size > 1) {
                        viewBinding.rcTabs.visible()
                        viewBinding.rcTabs.adapter = adapter
                        adapter.submitList(sheets.map { it.name })
                    } else {
                        viewBinding.rcTabs.gone()
                    }
                    loadSheet(0)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    viewBinding.progressBar.gone()
                    toast("Error loading file: ${e.message}")
                }
            }
        }
    }

    // ============================================================
    // LOAD SHEET
    // ============================================================

    private fun loadSheet(tabIndex: Int) {
        loadJob?.cancel()
        loadJob = null
        pendingRequest = null
        windowCache.clear()

        currentTab = tabIndex
        currentWindowStart = 0
        currentWindowEnd = 0
        mergeMap = emptyMap()

        viewBinding.progressBar.visible()

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val excelReader = reader ?: return@launch
                val info = excelReader.openSheet(tabIndex)
                metadata = info

                mergeMap = buildMergeMap(info)

                val windowSize = max(MIN_DOM_ROWS, DOM_WINDOW_ROWS)
                val initialEnd = minOf(info.maxRow, windowSize)

                val rows = excelReader.readRows(0, initialEnd)

                val actualStart = rows.firstOrNull()?.index ?: 0
                val actualEnd = if (rows.isNotEmpty()) rows.last().index + 1 else 0

                currentWindowStart = actualStart
                currentWindowEnd = actualEnd

                val html = buildInitialHtml(rows = rows, info = info)

                withContext(Dispatchers.Main) {
                    viewBinding.webView.loadDataWithBaseURL(
                        null,
                        html,
                        "text/html",
                        "UTF-8",
                        null
                    )
                    viewBinding.progressBar.gone()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    viewBinding.progressBar.gone()
                    toast("Error loading sheet: ${e.message}")
                }
            }
        }
    }

    // ============================================================
    // INITIAL HTML
    // ============================================================

    private fun buildInitialHtml(
        rows: List<StreamingExcelReader.Row>,
        info: StreamingExcelReader.SheetMetadata
    ): String {
        val body = renderRows(rows, info)
        val totalRows = info.maxRow
        val columnCount = maxOf(info.maxColumn + 1, 1)
        val columnHtml = buildColumnHtml(info, columnCount)

        val topHeight = currentWindowStart * ROW_HEIGHT
        val bottomRows = maxOf(0, totalRows - currentWindowEnd)
        val bottomHeight = bottomRows * ROW_HEIGHT

        return """
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta
    name="viewport"
    content="width=device-width, initial-scale=1.0, minimum-scale=0.25, maximum-scale=4.0, user-scalable=yes"
>
<style>
* { box-sizing: border-box; }

html, body {
    margin: 0;
    padding: 0;
    background: #f1f3f4;
    font-family: sans-serif;
    overflow-x: auto;
}

#root {
    width: max-content;
    min-width: 100%;
    background: white;
}

#top-spacer { height: ${topHeight}px; }
#bottom-spacer { height: ${bottomHeight}px; }

table {
    border-collapse: collapse;
    table-layout: fixed;
    width: max-content;
    min-width: 100%;
}

td {
    border: 1px solid #d0d0d0;
    padding: 5px 8px;
    height: ${ROW_HEIGHT}px;
    min-height: ${ROW_HEIGHT}px;
    max-height: ${ROW_HEIGHT}px;
    font-size: 14px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    vertical-align: middle;
    background: white;
}

td.row-number {
    width: 50px;
    min-width: 50px;
    max-width: 50px;
    background: #f5f5f5;
    color: #666;
    text-align: center;
    position: sticky;
    left: 0;
    z-index: 5;
}

#loading {
    position: fixed;
    bottom: 12px;
    right: 12px;
    padding: 8px 12px;
    border-radius: 8px;
    background: rgba(0, 0, 0, 0.65);
    color: white;
    font-size: 12px;
    display: none;
    z-index: 9999;
}
</style>
</head>
<body>
<div id="root">
    <div id="top-spacer"></div>
    <table>
        <colgroup>
            <col style="width: 50px; min-width: 50px;">
            $columnHtml
        </colgroup>
        <tbody id="excel-body">
            $body
        </tbody>
    </table>
    <div id="bottom-spacer"></div>
</div>
<div id="loading">Loading...</div>
<script>
(function() {
    let totalRows = $totalRows;
    let currentStart = $currentWindowStart;
    let currentEnd = $currentWindowEnd;

    const DOM_WINDOW_ROWS = $DOM_WINDOW_ROWS;
    const PRELOAD_ROWS = $PRELOAD_ROWS;
    const ROW_HEIGHT = $ROW_HEIGHT;
    const REQUEST_THROTTLE_MS = $REQUEST_THROTTLE_MS;

    let lastScrollTop = 0;
    let direction = 1;
    let requestedStart = -1;
    let requestedEnd = -1;
    let lastRequestTime = 0;
    let scrollTicking = false;

    function showLoading() {
        const el = document.getElementById("loading");
        if (el) el.style.display = "block";
    }

    function hideLoading() {
        const el = document.getElementById("loading");
        if (el) el.style.display = "none";
    }

    function getScrollTop() {
        return window.scrollY || document.documentElement.scrollTop || 0;
    }

    function getEstimatedRow() {
        const scrollTop = getScrollTop();
        if (totalRows <= 0) return 0;
        return Math.max(0, Math.min(totalRows - 1, Math.floor(scrollTop / ROW_HEIGHT)));
    }

    function calculateWindow(anchorRow) {
        let start = anchorRow - Math.floor(DOM_WINDOW_ROWS / 2);
        start = Math.max(0, start);
        let end = Math.min(totalRows, start + DOM_WINDOW_ROWS);

        if (end >= totalRows) {
            end = totalRows;
            start = Math.max(0, end - DOM_WINDOW_ROWS);
        }
        if (end <= start) {
            end = Math.min(totalRows, start + 1);
        }
        return { start: start, end: end };
    }

    function checkVirtualScroll() {
        if (totalRows <= 0) return;

        const scrollTop = getScrollTop();

        if (scrollTop > lastScrollTop) direction = 1;
        else if (scrollTop < lastScrollTop) direction = -1;
        lastScrollTop = scrollTop;

        const estimatedRow = getEstimatedRow();
        const outsideWindow = estimatedRow < currentStart || estimatedRow >= currentEnd;

        if (outsideWindow) {
            requestWindowAround(estimatedRow, direction);
            return;
        }

        const nearBottom = estimatedRow >= (currentEnd - PRELOAD_ROWS);
        if (direction > 0 && nearBottom && currentEnd < totalRows) {
            requestWindowAround(estimatedRow, 1);
            return;
        }

        const nearTop = estimatedRow <= (currentStart + PRELOAD_ROWS);
        if (direction < 0 && nearTop && currentStart > 0) {
            requestWindowAround(estimatedRow, -1);
            return;
        }
    }

    function requestWindowAround(anchorRow, loadDirection) {
        const now = Date.now();
        const outsideWindow = anchorRow < currentStart || anchorRow >= currentEnd;

        if (!outsideWindow && now - lastRequestTime < REQUEST_THROTTLE_MS) {
            return;
        }

        const window = calculateWindow(anchorRow);
        requestWindow(window.start, window.end, anchorRow, loadDirection);
    }

    function requestWindow(start, end, anchorRow, loadDirection) {
        start = Math.max(0, start);
        end = Math.min(totalRows, end);
        if (start >= end) return;

        if (start === currentStart && end === currentEnd) return;
        if (start === requestedStart && end === requestedEnd) return;

        requestedStart = start;
        requestedEnd = end;
        lastRequestTime = Date.now();
        showLoading();

        if (window.Android && Android.requestWindow) {
            Android.requestWindow(start, end, anchorRow, loadDirection);
        }
    }

    window.replaceWindow = function(base64Html, start, end, anchorRow, loadDirection) {
        const body = document.getElementById("excel-body");
        if (!body) {
            hideLoading();
            return;
        }

        const table = body.closest("table");
        if (table) table.style.visibility = "hidden";

        const oldScrollTop = getScrollTop();
        const anchorOffset = oldScrollTop - (anchorRow * ROW_HEIGHT);

        let html = "";
        try {
            const binary = atob(base64Html);
            const bytes = new Uint8Array(binary.length);
            for (let i = 0; i < binary.length; i++) {
                bytes[i] = binary.charCodeAt(i);
            }
            html = new TextDecoder("utf-8").decode(bytes);
        } catch (e) {
            html = decodeURIComponent(escape(atob(base64Html)));
        }

        body.innerHTML = html;
        currentStart = start;
        currentEnd = end;
        updateSpacers();

        let newScrollTop = (anchorRow * ROW_HEIGHT) + anchorOffset;
        newScrollTop = Math.max(0, newScrollTop);
        const maxScroll = Math.max(0, document.documentElement.scrollHeight - window.innerHeight);
        newScrollTop = Math.min(newScrollTop, maxScroll);
        window.scrollTo(0, newScrollTop);

        if (table) table.style.visibility = "visible";

        requestedStart = -1;
        requestedEnd = -1;
        hideLoading();

        requestAnimationFrame(function() {
            checkVirtualScroll();
        });
    };

    window.resetVirtualLoading = function() {
        requestedStart = -1;
        requestedEnd = -1;
        hideLoading();
    };

    function updateSpacers() {
        const topSpacer = document.getElementById("top-spacer");
        const bottomSpacer = document.getElementById("bottom-spacer");
        if (topSpacer) {
            topSpacer.style.height = (currentStart * ROW_HEIGHT) + "px";
        }
        if (bottomSpacer) {
            bottomSpacer.style.height = (Math.max(0, totalRows - currentEnd) * ROW_HEIGHT) + "px";
        }
    }

    window.addEventListener("scroll", function() {
        if (scrollTicking) return;
        scrollTicking = true;
        requestAnimationFrame(function() {
            scrollTicking = false;
            checkVirtualScroll();
        });
    }, { passive: true });

    window.checkVirtualScroll = checkVirtualScroll;

    requestAnimationFrame(function() {
        checkVirtualScroll();
    });
})();
</script>
</body>
</html>
        """.trimIndent()
    }

    // ============================================================
    // LOAD WINDOW
    // ============================================================

    private fun loadWindow(request: WindowRequest) {
        val info = metadata ?: return
        val maxRow = info.maxRow
        if (maxRow <= 0) {
            resetJsLoading()
            return
        }

        val safeStart = request.start.coerceIn(0, maxRow - 1)
        val safeEnd = request.end.coerceIn(safeStart + 1, maxRow)

        if (safeStart == currentWindowStart && safeEnd == currentWindowEnd) {
            resetJsLoading()
            return
        }

        // Cache hit?
        val cacheKey = safeStart to safeEnd
        val cachedHtml = windowCache[cacheKey]
        if (cachedHtml != null) {
            currentWindowStart = safeStart
            currentWindowEnd = safeEnd

            viewBinding.webView.evaluateJavascript(
                """
                if (window.replaceWindow) {
                    window.replaceWindow(
                        '$cachedHtml',
                        $safeStart,
                        $safeEnd,
                        ${request.anchorRow},
                        ${request.direction}
                    );
                }
                """.trimIndent(),
                null
            )
            return
        }

        loadJob = lifecycleScope.launch(Dispatchers.IO) {
            try {
                val excelReader = reader ?: return@launch

                val rows = excelReader.readRows(safeStart, safeEnd)
                if (rows.isEmpty()) {
                    withContext(Dispatchers.Main) { resetJsLoading() }
                    return@launch
                }

                val actualStart = rows.first().index
                val actualEnd = rows.last().index + 1

                val html = renderRows(rows, info)
                val encoded = Base64.encodeToString(
                    html.toByteArray(Charsets.UTF_8),
                    Base64.NO_WRAP
                )

                // Cache
                windowCache[actualStart to actualEnd] = encoded

                withContext(Dispatchers.Main) {
                    currentWindowStart = actualStart
                    currentWindowEnd = actualEnd

                    viewBinding.webView.evaluateJavascript(
                        """
                        if (window.replaceWindow) {
                            window.replaceWindow(
                                '$encoded',
                                $actualStart,
                                $actualEnd,
                                ${request.anchorRow},
                                ${request.direction}
                            );
                        }
                        """.trimIndent(),
                        null
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) { resetJsLoading() }
            } finally {
                withContext(Dispatchers.Main) {
                    loadJob = null

                    val pending = pendingRequest
                    pendingRequest = null

                    if (pending != null) {
                        if (pending.start != currentWindowStart || pending.end != currentWindowEnd) {
                            loadWindow(pending)
                        } else {
                            resetJsLoading()
                        }
                    }
                }
            }
        }
    }

    // ============================================================
    // RESET JS
    // ============================================================

    private fun resetJsLoading() {
        viewBinding.webView.evaluateJavascript(
            """
            if (window.resetVirtualLoading) {
                window.resetVirtualLoading();
            } else {
                if (typeof requestedStart !== 'undefined') requestedStart = -1;
                if (typeof requestedEnd !== 'undefined') requestedEnd = -1;
                if (typeof hideLoading === 'function') hideLoading();
            }
            """.trimIndent(),
            null
        )
    }

    // ============================================================
    // MERGE CACHE
    // ============================================================

    private fun buildMergeMap(
        info: StreamingExcelReader.SheetMetadata
    ): Map<Pair<Int, Int>, StreamingExcelReader.MergeRegion> {
        if (info.merges.isEmpty()) return emptyMap()

        val map = HashMap<Pair<Int, Int>, StreamingExcelReader.MergeRegion>()
        for (merge in info.merges) {
            for (row in merge.firstRow..merge.lastRow) {
                for (col in merge.firstCol..merge.lastCol) {
                    map[row to col] = merge
                }
            }
        }
        return map
    }

    // ============================================================
    // RENDER ROWS
    // ============================================================

    private fun renderRows(
        rows: List<StreamingExcelReader.Row>,
        info: StreamingExcelReader.SheetMetadata
    ): String {
        if (rows.isEmpty()) return ""

        val html = StringBuilder(rows.size * 1024)

        for (row in rows) {
            html.append("<tr>")

            // Row number
            html.append("<td class=\"row-number\">")
            html.append(row.index + 1)
            html.append("</td>")

            val cells = row.cells.associateBy { it.col }
            var col = 0

            while (col <= info.maxColumn) {
                val merge = mergeMap[row.index to col]

                if (merge != null) {
                    if (merge.startsAt(row.index, col)) {
                        val rowspan = merge.lastRow - merge.firstRow + 1
                        val colspan = merge.lastCol - merge.firstCol + 1
                        val cell = cells[merge.firstCol]

                        html.append("<td rowspan=\"")
                        html.append(rowspan)
                        html.append("\" colspan=\"")
                        html.append(colspan)
                        html.append("\">")
                        html.append(escapeHtml(cell?.value ?: ""))
                        html.append("</td>")

                        col = merge.lastCol + 1
                    } else {
                        col++
                    }
                    continue
                }

                val cell = cells[col]
                html.append("<td>")
                if (cell != null) {
                    html.append(escapeHtml(cell.value))
                }
                html.append("</td>")
                col++
            }

            html.append("</tr>")
        }

        return html.toString()
    }

    // ============================================================
    // COLUMN
    // ============================================================

    private fun buildColumnHtml(
        info: StreamingExcelReader.SheetMetadata,
        count: Int
    ): String {
        val html = StringBuilder(count * 100)

        for (index in 0 until count) {
            val column = info.columns[index]
            val widthPx = if (column != null) {
                maxOf(50, (column.width * 7).toInt())
            } else {
                100
            }

            html.append(
                """
                <col style="width: ${widthPx}px; min-width: ${widthPx}px;">
                """.trimIndent()
            )
        }
        return html.toString()
    }

    // ============================================================
    // HTML ESCAPE
    // ============================================================

    private fun escapeHtml(value: String): String {
        return value
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#39;")
            .replace("\n", "<br>")
            .replace("\r", "")
    }

    // ============================================================
    // TAB
    // ============================================================

    private fun onSelectTab() {
        adapter.setOnClickTab { tab ->
            loadSheet(tab)
        }
    }

    // ============================================================
    // FILE NAME
    // ============================================================

    private fun getNameFileFromUri(context: Context, uri: Uri?): String {
        if (uri == null) return MainActivity5.INVALID_NAME

        var fileName: String? = null

        if (uri.scheme == "content") {
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (index != -1) {
                        fileName = cursor.getString(index)
                    }
                }
            }
        }

        if (fileName == null) {
            fileName = uri.path?.let {
                val cut = it.lastIndexOf('/')
                if (cut != -1) it.substring(cut + 1) else it
            }
        }

        return fileName ?: MainActivity5.INVALID_NAME
    }

    // ============================================================
    // DESTROY
    // ============================================================

    override fun onDestroy() {
        loadJob?.cancel()
        loadJob = null
        pendingRequest = null
        windowCache.clear()

        reader?.close()
        reader = null

        tempFile?.delete()
        tempFile = null

        mergeMap = emptyMap()

        viewBinding.webView.removeJavascriptInterface("Android")
        super.onDestroy()
    }
}