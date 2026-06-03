package com.example.samplecleanarchitecture.base

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseActivity<VB : ViewBinding>(inflater: (LayoutInflater) -> VB) :
    AppCompatActivity() {

    protected val viewBinding by lazy { inflater(layoutInflater) }
    protected open val belowStatusBar = true

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        hideNavigationBar()
        if (belowStatusBar) {
            drawBelowStatusBar()
        }
        initView()
        bindView()//setup ui
        bindViewModel()//get data from viewmodel
    }

    open fun initView() {}

    open fun bindView() {}

    open fun bindViewModel() {}

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            hideNavigationBarInLowerAndroid()
        }
    }

    private fun hideNavigationBar() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            hideNavigationBarInLowerAndroid()
        } else {
            WindowInsetsControllerCompat(window, viewBinding.root).let { controller ->
                controller.hide(WindowInsetsCompat.Type.navigationBars())
                controller.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }

    private fun hideNavigationBarInLowerAndroid() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }

    private fun drawBelowStatusBar() {
        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.root) { v, insets ->
            val imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            val statusBarsInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            val insetBottom = if (imeVisible) imeInsets.bottom else statusBarsInsets.bottom
            v.setPadding(
                statusBarsInsets.left, statusBarsInsets.top, statusBarsInsets.right, insetBottom
            )
            insets
        }
    }

    protected fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    protected fun log(message: String) {
        Log.d("TEST", message)
    }

    open fun showActivity(activity: Class<*>, bundle: Bundle? = null) {
        val intent = Intent(this, activity)
        intent.putExtras(bundle ?: Bundle())
        startActivity(intent)
    }

    open fun observerData(
        block: suspend CoroutineScope.() -> Unit = {},
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                block()
            }
        }
    }

    open fun onFinish() {
        finish()
    }
}
