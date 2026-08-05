package com.example.testBaseApp.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.testBaseApp.R
import com.example.testBaseApp.base.BaseActivity
import com.example.testBaseApp.databinding.ActivityMain6Binding

class MainActivity6 : BaseActivity<ActivityMain6Binding>(ActivityMain6Binding::inflate) {

    private lateinit var json: String

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        json = intent.getStringExtra(MainActivity5.KEY_JSON_FILE) ?: "[]"
        viewBinding.webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            allowFileAccess = true
            builtInZoomControls = true
            displayZoomControls = false
        }

        viewBinding.webView.apply {
            webViewClient = object : WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    evaluateJavascript(
                        "loadData('$json')", null
                    )
                }
            }
            loadUrl("file:///android_asset/excel_viewer.html")
        }
    }
}