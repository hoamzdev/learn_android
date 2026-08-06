package com.example.testBaseApp.ui.main

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.aspose.cells.HtmlSaveOptions
import com.aspose.cells.License
import com.aspose.cells.Workbook
import com.example.testBaseApp.base.BaseActivity
import com.example.testBaseApp.databinding.ActivityMain6Binding
import com.example.testBaseApp.ui.main.MainActivity5.Companion.KEY_JSON_FILE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

class MainActivity6 : BaseActivity<ActivityMain6Binding>(ActivityMain6Binding::inflate) {

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {

        val targetUri = intent.data ?: intent.getStringExtra(KEY_JSON_FILE)?.toUri()
        if (targetUri == null) {
            Toast.makeText(this, "Không nhận được URI file!", Toast.LENGTH_SHORT).show()
            return
        }

        viewBinding.webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true

            allowFileAccess = true
            allowContentAccess = true
            builtInZoomControls = true
            displayZoomControls = false
            setSupportZoom(true)

            allowFileAccessFromFileURLs = true
            allowUniversalAccessFromFileURLs = true
        }

        Log.d("EXCEL_DEBUG", "Bắt đầu đọc URI qua Aspose.Cells: $targetUri")
        toast("start reading")
        lifecycleScope.launch(Dispatchers.IO) {
            renderExcelLikePC(targetUri)
        }
    }

    private suspend fun renderExcelLikePC(fileUri: Uri) {
        try {
            val inputStream = contentResolver.openInputStream(fileUri)
                ?: throw IllegalArgumentException("Cannot open InputStream")

            val workbook = inputStream.use { Workbook(it) }

            val options = HtmlSaveOptions().apply {
                exportImagesAsBase64 = true
                exportGridLines = true
                exportActiveWorksheetOnly = true
                presentationPreference = true
            }

            val htmlDir = File(cacheDir, "excel_html").apply {
                if (!exists()) mkdirs()
            }

            val htmlFile = File(htmlDir, "index.html")

            workbook.save(htmlFile.absolutePath, options)


            val html = htmlFile.readText(Charsets.UTF_8)

// Chỉ thay padding trái/phải


            htmlFile.writeText(html)

            Log.d("EXCEL_DEBUG", "HTML: ${htmlFile.absolutePath}")

            withContext(Dispatchers.Main) {
                viewBinding.webView.loadUrl(htmlFile.toURI().toString())
            }

        } catch (e: Exception) {
            Log.e("EXCEL_DEBUG", "Error", e)
        }
    }
}