package com.example.samplecleanarchitecture.utils

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CAMERA_SERVICE
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.ImageDecoder
import android.graphics.ImageFormat
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.Drawable
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Size
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.PathParser
import androidx.core.graphics.createBitmap
import androidx.core.graphics.toColorInt
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.children
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import org.xmlpull.v1.XmlPullParser
import java.io.ByteArrayOutputStream
import kotlin.math.abs


fun dpToPx(context: Context, dp: Float): Float {
    val density = context.resources.displayMetrics.density
    return dp * density
}


fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


fun View.setAllEnabled(enabled: Boolean) {
    isEnabled = enabled
    if (this is ViewGroup) children.forEach { child -> child.setAllEnabled(enabled) }
}


fun TextView.afterTextChanged(callback: (String) -> Unit) {
    val handler = Handler(Looper.getMainLooper())
    var runnable: Runnable? = null

    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            runnable?.let { handler.removeCallbacks(it) }
            runnable = Runnable { callback(s.toString()) }
            handler.postDelayed(runnable!!, 500)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun TextView.keyDown(callback: (Int) -> Unit) {
    this.setOnKeyListener { _, keyCode, event ->
        if (event.action == KeyEvent.ACTION_DOWN) {
            callback.invoke(keyCode)
            true
        } else {
            false
        }
    }
}

fun TextView.beforeTextChanged(callback: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            callback(s.toString())
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun TextView.textChanged(callback: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            callback(s.toString())
        }
    })
}


fun View.fadeIn(duration: Long = 300) {
    this.alpha = 0f
    this.visibility = View.VISIBLE
    ObjectAnimator.ofFloat(this, "alpha", 1f).setDuration(duration).start()
}

fun View.fadeOut(duration: Long = 300) {
    ObjectAnimator.ofFloat(this, "alpha", 0f).setDuration(duration).start()
    this.postDelayed({ this.visibility = View.GONE }, duration)
}


fun ImageView.loadImage(
    url: String,
    onSuccess: (() -> Unit)? = null,
    onError: ((Exception?) -> Unit)? = null,
) {
    Glide.with(this.context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                p0: GlideException?,
                p1: Any?,
                p2: Target<Drawable?>,
                p3: Boolean,
            ): Boolean {
                onError?.invoke(p0)
                return false
            }

            override fun onResourceReady(
                p0: Drawable,
                p1: Any,
                p2: Target<Drawable?>?,
                p3: DataSource,
                p4: Boolean,
            ): Boolean {
                onSuccess?.invoke()
                return false
            }
        }).into(this)
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun dpToPx(activity: FragmentActivity, dp: Int): Int {
    val density = activity.resources.displayMetrics.density
    return (dp * density).toInt()
}


@SuppressLint("DefaultLocale")
fun convertMillieToHhMmSs(millis: Long): String {
    val seconds = millis / 1000
    val second = seconds % 60
    val minute = seconds / 60 % 60
    val hour = seconds / (60 * 60) % 24
    return if (hour > 0) {
        String.format("%02d:%02d:%02d", hour, minute, second)
    } else {
        String.format("%02d:%02d", minute, second)
    }
}

@SuppressLint("DefaultLocale")
fun Long.toMinutesSeconds(): String {
    val minutes = this / 60
    val seconds = this % 60
    return String.format("%02d:%02d", minutes, seconds) // Format kiểu 00:00
}

fun delays(time: Long, content: () -> Unit) {
    Looper.myLooper()?.let { Handler(it).postDelayed({ content.invoke() }, time) }
}

fun getScreenDimensions(context: Context): Pair<Int, Int> {
    val displayMetrics = DisplayMetrics()
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    windowManager.defaultDisplay.getMetrics(displayMetrics)

    val screenWidth = displayMetrics.widthPixels
    val screenHeight = displayMetrics.heightPixels

    return Pair(screenWidth, screenHeight)
}

fun delays(time: Int, content: () -> Unit) {
    Looper.myLooper()?.let { Handler(it).postDelayed({ content.invoke() }, time.toLong()) }
}


fun bitmapToBase64(bitmap: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 35, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

fun Bitmap.toBase64(): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 35, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

fun base64ToBitmap(base64String: String): Bitmap? {
    val byteArray = Base64.decode(base64String, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

fun captureView(view: View): Bitmap {
    val bitmap = createBitmap(view.width, view.height)
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
}

// Ẩn bàn phím
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

// Hiển thị bàn phím
fun View.showKeyboard() {
    requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun fromColor(code: String): Int {
    val cleanedCode = code.replace("#", "").replace(" ", "")
    return "#$cleanedCode".toColorInt()
}

fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Copied Text", text)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(context, "Text has been copied", Toast.LENGTH_SHORT).show()
}

fun shareText(context: Context, text: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    context.startActivity(Intent.createChooser(intent, "Share to"))
}

fun hideSystemUI(window: Window, container: View) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowInsetsControllerCompat(window, container).let { controller ->
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

fun showSystemUI(window: Window, container: View) {
    WindowCompat.setDecorFitsSystemWindows(window, true)
    WindowInsetsControllerCompat(window, container).show(WindowInsetsCompat.Type.systemBars())
}

fun getScreenResolution(context: Context): Pair<Int, Int> {
    val displayMetrics = context.resources.displayMetrics
    return Pair(displayMetrics.widthPixels, displayMetrics.heightPixels)
}


fun getPathsByNames(context: Context, drawableRes: Int, names: Set<String>): List<Path> {
    val parser = context.resources.getXml(drawableRes)
    val androidNs = "http://schemas.android.com/apk/res/android"
    val result = mutableListOf<Path>()
    var eventType = parser.eventType

    while (eventType != XmlPullParser.END_DOCUMENT) {
        if (eventType == XmlPullParser.START_TAG && parser.name == "path") {
            val attrName = parser.getAttributeValue(androidNs, "name")
            if (names.contains(attrName)) {
                val pathData = parser.getAttributeValue(androidNs, "pathData")
                PathParser.createPathFromPathData(pathData)?.let { result.add(it) }
            }
        }
        eventType = parser.next()
    }
    return result
}


fun Uri.toBitmap(context: Context): Bitmap? {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, this)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, this)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Image.toBitmap(): Bitmap {
    val buffer = planes[0].buffer
    val bytes = ByteArray(buffer.remaining())
    buffer.get(bytes)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}

fun applyColorMatrixToBitmap(source: Bitmap, matrix: ColorMatrix?): Bitmap? {
//    val result = source.config?.let { createBitmap(source.width, source.height, it) }
//    val canvas = result?.let { Canvas(it) }
//    val paint = Paint()
//    matrix?.let { paint.colorFilter = ColorMatrixColorFilter(it) }
//    canvas?.drawBitmap(source, 0f, 0f, paint)
//    return result

    val result = source.copy(Bitmap.Config.ARGB_8888, true)
    val canvas = Canvas(result)
    val paint = Paint().apply {
        alpha = (0.4f * 255).toInt()
        colorFilter = matrix?.let { ColorMatrixColorFilter(it) }
    }
    canvas.drawRect(0f, 0f, source.width.toFloat(), source.height.toFloat(), paint)
    return result

}


fun ColorMatrix.toJsonString(): String {
    return this.array.joinToString(separator = ",")
}

fun jsonStringToColorMatrix(json: String): ColorMatrix {
    val values = json.split(",").map { it.toFloat() }
    return ColorMatrix(values.toFloatArray())
}

fun AppCompatImageView.setTint(@ColorRes colorRes: Int) {
    ImageViewCompat.setImageTintList(
        this, ColorStateList.valueOf(ContextCompat.getColor(this.context, colorRes))
    )
}


