package com.example.testBaseApp.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import java.io.File
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.example.testBaseApp.base.BaseActivity
import com.example.testBaseApp.databinding.ActivityMain6Binding
import com.example.testBaseApp.ui.main.MainActivity5.Companion.INVALID_NAME
import android.webkit.JavascriptInterface
import com.example.testBaseApp.ui.ExcelHtmlConverter
import com.example.testBaseApp.ui.adapter.TabSheetAdapter
import com.example.testBaseApp.utils.gone
import com.example.testBaseApp.utils.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory

class MainActivity6 : BaseActivity<ActivityMain6Binding>(ActivityMain6Binding::inflate) {

    private val uri: Uri? by lazy { intent.getStringExtra(MainActivity5.KEY_JSON_FILE)?.toUri() }

    private val adapter by lazy { TabSheetAdapter() }

    private var workbook : Workbook? = null
    private var currentTab = 0
    private var currentRowIndex = 0
    private val PAGE_SIZE = 100
    private var sheetInfo: ExcelHtmlConverter.SheetInfo? = null

    override val belowStatusBar: Boolean
        get() = false

    override fun initView() {
        setUpWebView()
        uri?.let { loadExcelFromUri(it) }
        val nameFile = getNameFileFromUri(this, uri)
        viewBinding.tvNameFile.text = nameFile
    }


    private fun setUpWebView(){
        viewBinding.webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            builtInZoomControls = true
            displayZoomControls = false
            setSupportZoom(true)
            textZoom = 115
            useWideViewPort = true
            loadWithOverviewMode = true
            mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        viewBinding.webView.webViewClient = object : android.webkit.WebViewClient() {
            override fun onPageFinished(view: android.webkit.WebView?, url: String?) {
                super.onPageFinished(view, url)
                view?.evaluateJavascript("checkScroll();", null)
            }

            override fun shouldOverrideUrlLoading(
                view: android.webkit.WebView?,
                request: android.webkit.WebResourceRequest?
            ): Boolean {
                val url = request?.url?.toString()
                if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                        startActivity(intent)
                        return true
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                return false
            }
        }
        viewBinding.webView.addJavascriptInterface(this, "Android")
        viewBinding.webView.setInitialScale(140)
    }

    @JavascriptInterface
    fun loadMoreRows() {
        lifecycleScope.launch(Dispatchers.Main) {
            val info = sheetInfo ?: return@launch
            if (currentRowIndex >= info.totalRowsToRender) {
                viewBinding.webView.evaluateJavascript("setHasMore(false);", null)
                return@launch
            }

            val nextChunkEnd = minOf(currentRowIndex + PAGE_SIZE, info.totalRowsToRender)
            val sheet = workbook?.getSheetAt(currentTab) ?: return@launch
            
            val htmlChunk = withContext(Dispatchers.IO) {
                ExcelHtmlConverter.renderRowRange(sheet, currentRowIndex, nextChunkEnd, info)
            }
            
            currentRowIndex = nextChunkEnd
            
            if (htmlChunk.isBlank()) {
                viewBinding.webView.evaluateJavascript("setHasMore(false);", null)
                return@launch
            }

            // Thoát dấu backslash, nháy đơn và các ký tự xuống dòng
            val escapedHtml = htmlChunk
                .replace("\\", "\\\\")
                .replace("'", "\\'")
                .replace("\n", "")
                .replace("\r", "")

            viewBinding.webView.evaluateJavascript("appendRows('$escapedHtml');", null)
            
            if (currentRowIndex >= info.totalRowsToRender) {
                viewBinding.webView.evaluateJavascript("setHasMore(false);", null)
            }
        }
    }

    //load from uri
    private fun loadExcelFromUri(uri : Uri){
        viewBinding.progressBar.visible()
        lifecycleScope.launch(Dispatchers.IO) {
            try{
                // Copy stream ra file tạm để WorkbookFactory.create(File) chạy nhanh và tiết kiệm RAM hơn
                val tempFile = File(cacheDir, "temp_excel_${System.currentTimeMillis()}.bin")
                contentResolver.openInputStream(uri)?.use { input ->
                    tempFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }

                if (tempFile.exists()) {
                    workbook = WorkbookFactory.create(tempFile)
                    val totalsSheet = workbook?.numberOfSheets ?: 0
                    
                    withContext(Dispatchers.Main) {
                        if (totalsSheet <= 1) {
                            viewBinding.rcTabs.gone()
                        } else {
                            loadTabToRcView(totalsSheet)
                        }
                        loadSheet(0)
                        tempFile.delete()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("MainActivity6", "loadExcelFromUri: ${e.message}")
                withContext(Dispatchers.Main) {
                    toast("Error loading file: ${e.message}")
                    viewBinding.progressBar.gone()
                }
            }
        }
    }

    private fun loadSheet(tabIndex: Int) {
        currentTab = tabIndex
        currentRowIndex = 0
        viewBinding.progressBar.visible()
        
        lifecycleScope.launch(Dispatchers.IO) {
            val sheet = workbook?.getSheetAt(tabIndex) ?: return@launch
            sheetInfo = ExcelHtmlConverter.prepareSheetInfo(sheet)
            
            val info = sheetInfo!!
            val initialEnd = minOf(PAGE_SIZE, info.totalRowsToRender)
            
            val htmlHeader = ExcelHtmlConverter.getHtmlHeader(sheet, info)
            val initialRows = ExcelHtmlConverter.renderRowRange(sheet, 0, initialEnd, info)
            val htmlFooter = ExcelHtmlConverter.getHtmlFooter()
            
            currentRowIndex = initialEnd
            
            val fullHtml = htmlHeader + initialRows + htmlFooter
            
            withContext(Dispatchers.Main) {
                loadHtml(fullHtml)
                if (currentRowIndex >= info.totalRowsToRender) {
                    viewBinding.webView.evaluateJavascript("setHasMore(false);", null)
                }
            }
        }
    }

    private fun loadTabToRcView(tabs : Int){
        val sheetNames = mutableListOf<String>()
        repeat(tabs){ index ->
            val sheetName = workbook?.getSheetName(index) ?: "Sheet ${index + 1}"
            sheetNames.add(sheetName)
        }
        if(sheetNames.isNotEmpty()){
            viewBinding.rcTabs.visible()
            viewBinding.apply {
                rcTabs.adapter = adapter
            }
        }
        adapter.submitList(sheetNames)
        onSelectTab()
    }

    private fun onSelectTab(){
        adapter.setOnClickTab { tab ->
            loadSheet(tab)
        }
    }

    private fun loadHtml(html : String){
        viewBinding.webView.apply {
            loadDataWithBaseURL(
                null,
                html,
                "text/html",
                "UTF-8",
                null
            )
        }
        viewBinding.progressBar.gone()
    }

    private fun getNameFileFromUri(context: Context, uri: Uri?): String {
        if (uri == null) return INVALID_NAME
        var fileName: String? = ""
        if (uri.scheme == "content") {
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                if(cursor.moveToFirst()){
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if(nameIndex != -1){
                        fileName = cursor.getString(nameIndex)
                    }
                }
            }
        }
        if(fileName == null){
            //file:///storage/emulated/0/Download/document.pdf
            fileName = uri.path?.let {
                val cut = it.lastIndexOf('/')
                if (cut != -1) {
                    it.substring(cut + 1)
                } else {
                    it
                }
            }
        }
        return fileName ?: INVALID_NAME
    }
}