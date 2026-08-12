//package com.example.testBaseApp.utils
//
//import android.animation.ValueAnimator
//import android.annotation.SuppressLint
//import android.app.Activity
//import android.content.ContentResolver
//import android.content.ContentValues
//import android.content.Context
//import android.content.Intent
//import android.content.pm.ShortcutInfo
//import android.content.pm.ShortcutManager
//import android.database.sqlite.SQLiteDatabase
//import android.graphics.drawable.Icon
//import android.net.Uri
//import android.os.Build
//import android.os.Environment
//import android.provider.MediaStore
//import android.text.SpannableString
//import android.text.TextUtils
//import android.text.style.ForegroundColorSpan
//import android.view.View
//import androidx.core.content.ContextCompat
//import androidx.core.net.toUri
//import androidx.core.view.isGone
//import androidx.room.util.getColumnIndex
//import com.example.testBaseApp.R
//import com.example.testBaseApp.ui.main.MainActivity6
//import com.example.testBaseApp.ui.main.ViewOfficeActivity
//import com.example.testBaseApp.ui.obj.OfficeModel
//import java.io.File
//import java.io.FileOutputStream
//import java.nio.file.FileSystems
//import java.text.SimpleDateFormat
//import java.util.Collections
//import java.util.Date
//import java.util.Locale
//import java.util.Objects
//
//object UtilsTest {
//    class HideView(private val view: View) : ValueAnimator.AnimatorUpdateListener {
//        override fun onAnimationUpdate(animation: ValueAnimator) {
//            val height = animation.animatedValue as Int
//            val params = view.layoutParams
//            params.height = height
//            view.layoutParams = params
//            if (height == 0) {
//                view.visibility = View.GONE
//            }
//        }
//    }
//
//    class ShowView(private val view: View) : ValueAnimator.AnimatorUpdateListener {
//        override fun onAnimationUpdate(animation: ValueAnimator) {
//            val height = animation.animatedValue as Int
//            val params = view.layoutParams
//            params.height = height
//            view.layoutParams = params
//        }
//    }
//
////    /** * Request storage permission / open all-files-access settings. */
////    fun requestStoragePermission(activity: Activity) {
////        if (Build.VERSION.SDK_INT < 30) {
////            Dexter.withContext(activity).withPermissions(
////                "android.permission.READ_EXTERNAL_STORAGE",
////                "android.permission.WRITE_EXTERNAL_STORAGE"
////            ).withListener(object : MultiplePermissionsListener {
////                override fun onPermissionsChecked(report: MultiplePermissionsReport) {}
////                override fun onPermissionRationaleShouldBeShown(
////                    permissions: List<*>,
////                    token: PermissionToken
////                ) {
////                }
////            }).onSameThread().check()
////            return
////        }
////        try {
////            val intent =
////                Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION").apply {
////                    data = "package:${activity.packageName}".toUri()
////                }
////            if (activity.packageManager.resolveActivity(
////                    intent,
////                    PDFWidget.PDF_BTN_FIELD_IS_PUSHBUTTON
////                ) != null
////            ) {
////                activity.startActivityForResult(intent, 2296)
////                return
////            }
////        } catch (_: Exception) {
////        }
////        try {
////            val intent =
////                Intent("android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION")
////            if (activity.packageManager.resolveActivity(
////                    intent,
////                    PDFWidget.PDF_BTN_FIELD_IS_PUSHBUTTON
////                ) != null
////            ) {
////                activity.startActivityForResult(intent, 2296)
////                return
////            }
////        } catch (_: Exception) {
////        }
////        try {
////            val intent = Intent("android.settings.APPLICATION_DETAILS_SETTINGS").apply {
////                data = "package:${activity.packageName}".toUri()
////            }
////            activity.startActivityForResult(intent, 2296)
////        } catch (_: Exception) {
////        }
////    }
//
//    /** * Check whether the app has storage access. */
//    fun hasStoragePermission(context: Context): Boolean {
//        return if (Build.VERSION.SDK_INT >= 30) {
//            Environment.isExternalStorageManager()
//        } else {
//            ContextCompat.checkSelfPermission(
//                context,
//                "android.permission.READ_EXTERNAL_STORAGE"
//            ) == 0 || ContextCompat.checkSelfPermission(
//                context,
//                "android.permission.WRITE_EXTERNAL_STORAGE"
//            ) == 0
//        }
//    }
//
//    /** * Copy a content:// or file:// URI to a destination file. */
//    @SuppressLint("Recycle")
//    fun copyUriToFile(mainActivity: MainActivity6, uri: Uri, destinationPath: String) {
//        if (uri.scheme != "content" && uri.scheme != "file") {
//            android.util.Log.e("Utils", "Unsupported URI scheme: $uri")
//            return
//        }
//        try {
//            if (isFilePendingOrTrashed(mainActivity, uri)) {
//                android.util.Log.e("Utils", "File is pending or trashed: $uri")
//                return
//            }
//            val inputStream =
//                mainActivity.contentResolver.openInputStream(uri)
//            if (inputStream == null) {
//                android.util.Log.e("Utils", "Unable to open input stream for $uri")
//                return
//            }
//            inputStream.use { input ->
//                FileOutputStream(File(destinationPath)).use { output ->
//                    val buffer = ByteArray(PDFWidget.PDF_TX_FIELD_IS_PASSWORD)
//                    while (true) {
//                        val count = input.read(buffer)
//                        if (count == -1) {
//                            break
//                        }
//                        output.write(buffer, 0, count)
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            android.util.Log.e("Utils", "Error copying file from $uri to $destinationPath", e)
//        }
//    }
//
//    /** * Format timestamp. */
//    fun formatDate(timestamp: Long?): String {
//        return SimpleDateFormat("MMM dd yyyy", Locale.getDefault()).format(
//            Date(
//                timestamp ?: 0L
//            )
//        )
//    }
//
//    /**
//     * Get Excel files from MediaStore.
//     */
//    fun getExcelFiles(context: Context): ArrayList<OfficeModel> {
//        val files = ArrayList<OfficeModel>()
//
//        val uri = MediaStore.Files.getContentUri("external_primary")
//
//        val projection = arrayOf(
//            "_display_name",
//            "date_added",
//            "_data",
//            "mime_type"
//        )
//
//        val selection = """
//        mime_type LIKE 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
//        OR mime_type LIKE 'application/vnd.ms-excel'
//        OR mime_type LIKE 'application/vnd.ms-excel.sheet.macroenabled.12'
//        OR mime_type LIKE 'application/vnd.ms-excel.template.macroenabled.12'
//        OR mime_type LIKE 'application/vnd.openxmlformats-officedocument.spreadsheetml.template'
//    """.trimIndent()
//
//        context.contentResolver.query(
//            uri,
//            projection,
//            selection,
//            null,
//            "date_added DESC"
//        )?.use { cursor ->
//
//            val pathIndex = cursor.getColumnIndex("_data")
//            val nameIndex = cursor.getColumnIndex("_display_name")
//
//            if (pathIndex == -1 || nameIndex == -1) {
//                return files
//            }
//
//            if (cursor.moveToFirst()) {
//                do {
//                    val path = cursor.getString(pathIndex)
//                    val name = cursor.getString(nameIndex)
//
//                    if (path != null && name != null) {
//                        val file = File(path)
//
//                        if (file.exists() && file.length() > 0) {
//                            val officeFile = OfficeModel(
//                                absolutePath = file.absolutePath,
//                                fileUri = file.absolutePath,
//                                length = file.length(),
//                                lastModified = file.lastModified(),
//                                nameFile = file.name,
//                                isDirectory = file.isDirectory
//                            )
//
//                            files.add(officeFile)
//                        }
//                    }
//
//                } while (cursor.moveToNext())
//            }
//        }
//
//        return files
//    }
//
//
//    /** * Get the display name of a content URI. */
//    fun getFileName(contentResolver: ContentResolver, uri: Uri): String? {
//        if (uri.scheme.equals("content", ignoreCase = true)) {
//            try {
//                contentResolver.query(uri, arrayOf("_display_name"), null, null, null)
//                    ?.use { cursor ->
//                        if (cursor.moveToFirst()) {
//                            return cursor.getString(cursor.getColumnIndexOrThrow("_display_name"))
//                        }
//                    }
//            } catch (_: Exception) {
//            }
//        }
//        return uri.lastPathSegment ?: "unknown_file${System.currentTimeMillis()}"
//    }
//
//    /** * Check whether MediaStore file is pending or trashed. */
//    fun isFilePendingOrTrashed(mainActivity: MainActivity6, uri: Uri): Boolean {
//        return try {
//            mainActivity.contentResolver.query(
//                uri,
//                arrayOf("is_pending", "is_trashed"),
//                null,
//                null,
//                null
//            )?.use { cursor ->
//                if (cursor.moveToFirst()) {
//                    val isPending = cursor.getInt(cursor.getColumnIndexOrThrow("is_pending"))
//                    val isTrashed =
//                        cursor.getInt(cursor.getColumnIndexOrThrow("is_trashed"))
//                    isPending == 1 || isTrashed == 1
//                } else {
//                    false
//                }
//            } ?: false
//        } catch (_: Exception) {
//            false
//        }
//    }
//
//    /** * Open an Office file in ViewOfficeActivity. */
//    fun openOfficeFile(activity: Activity, file: File) {
//        try {
//            val dbHelper = DbHelper.getInstance(activity)
//            val absolutePath = file.absolutePath
//            val database: SQLiteDatabase = dbHelper.writableDatabase
//            val values = ContentValues().apply {
//                put(
//                    "path",
//                    absolutePath
//                )
//            }
//            database.replace("history_pdfs", null, values)
//            dbHelper.close()
//            val intent = Intent(activity, ViewOfficeActivity::class.java).apply {
//                action = Intent.ACTION_VIEW
//                data = Uri.fromFile(file)
//                putExtra("KEY_SELECTED_FILE_URI", file.absolutePath)
//                putExtra("KEY_SELECTED_FILE_NAME", file.name)
//                putExtra("STARTED_FROM_EXPLORER", true)
//                putExtra("START_PAGE", 0)
//            }
//            activity.startActivity(intent)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    /** * Get filename without extension. */
//    fun getFileNameWithoutExtension(path: String): String {
//        val separator =
//            FileSystems.getDefault().separator
//        Objects.requireNonNull(separator)
//        var result = path
//        val separatorIndex = result.lastIndexOf(separator)
//        if (separatorIndex != -1) {
//            result = result.substring(separatorIndex + 1)
//        }
//        val extensionIndex = result.lastIndexOf(".")
//        return if (extensionIndex == -1) {
//            result
//        } else {
//            result.substring(0, extensionIndex)
//        }
//    }
//
//    /** * Build "Excel Reader" title. */
//    fun getAppTitle(mainActivity: MainActivity6): CharSequence {
//        val excel = SpannableString("Excel")
//        val reader = SpannableString(" Reader")
//        excel.setSpan(
//            ForegroundColorSpan(
//                mainActivity.getColor(R.color.color_37B43D)
//            ), 0, excel.length, 33
//        )
//        reader.setSpan(
//            ForegroundColorSpan(mainActivity.getColor(R.color.color_4E71FF)),
//            0,
//            reader.length,
//            33
//        )
//        return TextUtils.concat(excel, reader)
//    }
//
//    /** * Show/hide a view with animation. */
//    @SuppressLint("Recycle")
//    fun animateView(dimensionResId: Int, context: Context, view: View?, show: Boolean) {
//        if (view == null) {
//            return
//        }
//        val animator: ValueAnimator
//        val listener: ValueAnimator.AnimatorUpdateListener
//        if (view.isGone && show) {
//            val targetHeight =
//                context.resources.getDimensionPixelSize(dimensionResId)
//            view.layoutParams = view.layoutParams.apply {
//                height = 0
//            }
//            view.visibility = View.VISIBLE
//            animator = ValueAnimator.ofInt(
//                0,
//                targetHeight
//            )
//            animator.duration = 350L
//            listener = ShowView(view)
//        } else {
//            if (view.visibility != View.VISIBLE) {
//                return
//            }
//            animator = ValueAnimator.ofInt(view.height, 0)
//            animator.duration = 350L
//            listener = HideView(view)
//        }
//        animator.addUpdateListener(listener)
//        animator.start()
//    }
//
//    /** * Find an existing shortcut for a file. */
//    fun findShortcut(context: Context, officeModel: OfficeModel): ShortcutInfo? {
//        val shortcutManager =
//            context.getSystemService(ShortcutManager::class.java) ?: return null
//        val shortcuts =
//            ArrayList<ShortcutInfo>()
//        shortcuts.addAll(shortcutManager.pinnedShortcuts)
//        shortcuts.addAll(shortcutManager.dynamicShortcuts)
//        for (shortcut in shortcuts) {
//            val intent =
//                shortcut.intent
//            if (intent != null && intent.data != null && officeModel.fileUri == intent.data.toString()) {
//                return shortcut
//            }
//        }
//        return null
//    }
//
//    /** * Update a shortcut for a file. */
//    fun updateShortcut(context: Context, shortcutId: String, officeModel: OfficeModel) {
//        val shortcutManager = context.getSystemService(ShortcutManager::class.java) ?: return
//        val launchIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
//            ?: return
//        launchIntent.action = Intent.ACTION_CREATE_SHORTCUT
//        launchIntent.data = Uri.parse(officeModel.fileUri)
//        val shortcut = ShortcutInfo.Builder(context, shortcutId)
//            .setShortLabel(File(officeModel.absolutePath.toString()).name)
//            .setIcon(Icon.createWithResource(context, R.drawable.bg_12_unclick))
//            .setIntent(launchIntent)
//            .build()
//        shortcutManager.updateShortcuts(Collections.singletonList(shortcut))
//    }
//}