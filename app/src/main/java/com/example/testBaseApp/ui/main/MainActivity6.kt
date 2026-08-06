package com.example.testBaseApp.ui.main

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.example.testBaseApp.base.BaseActivity
import com.example.testBaseApp.databinding.ActivityMain6Binding
import com.example.testBaseApp.ui.ExcelHtmlConverter
import com.example.testBaseApp.utils.gone
import com.example.testBaseApp.utils.invisible
import com.example.testBaseApp.utils.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity6 : BaseActivity<ActivityMain6Binding>(ActivityMain6Binding::inflate) {

    private val uri: Uri? by lazy { intent.getStringExtra(MainActivity5.KEY_JSON_FILE)?.toUri() }

    override fun initView() {
        setUpWebView()
        uri?.let { loadExcelFromUri(it) }
    }


    private fun setUpWebView(){
        viewBinding.webView.settings.apply {
            javaScriptEnabled = false
            builtInZoomControls = true
            displayZoomControls = false
            setSupportZoom(true)
            textZoom = 125
            useWideViewPort = true
            loadWithOverviewMode = true
        }

        viewBinding.webView.setInitialScale(140)
    }

    //load from uri
    private fun loadExcelFromUri(uri : Uri){
        viewBinding.progressBar.visible()
        lifecycleScope.launch(Dispatchers.IO) {
            try{
                contentResolver.openInputStream(uri)?.use {
                    val htmlContent = ExcelHtmlConverter.convertToHtml(it)
                    withContext(Dispatchers.Main){
                        viewBinding.webView.apply {
                            loadDataWithBaseURL(
                                null,
                                htmlContent,
                                "text/html",
                                "UTF-8",
                                null
                            )
                        }
                        viewBinding.progressBar.gone()
                    }
                } ?: run {
                    toast("Can't not open file from input stream")
                }
            }catch (e : Exception){
                e.printStackTrace()
                toast("${e.message}")
                Log.d("MainActivity6", "loadExcelFromUri: ${e.message}")
            }
        }
    }

    //load from absolute path

}