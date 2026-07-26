package com.example.testBaseApp.utils


import android.graphics.Rect
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

fun Window.hideNavigation() {
    if (setFullScreenWallpaper()) return

    decorView.viewTreeObserver.addOnGlobalLayoutListener {
        val rect = Rect()
        val activityRoot = decorView
        activityRoot.getWindowVisibleDisplayFrame(rect)
        if (setFullScreenWallpaper()) return@addOnGlobalLayoutListener
    }
}

fun Window.hideStatusBar() {
    val flags: Int = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN
            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    decorView.systemUiVisibility = flags
    val decorView: View = decorView
    decorView.setOnSystemUiVisibilityChangeListener { visibility ->
        if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
            decorView.systemUiVisibility = flags
        }
    }
}
 fun Window.setFullScreenWallpaper(): Boolean {
     val windowInsetsController = ViewCompat.getWindowInsetsController(decorView) ?: return true

     // Đảm bảo layout không bị đẩy lên khi ẩn thanh điều hướng
    addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

    // Cho phép người dùng vuốt để hiện lại thanh điều hướng khi cần
    windowInsetsController.systemBarsBehavior =
        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

    // Ẩn Navigation Bar, nhưng vẫn hiển thị Status Bar
    windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())

    return false
}