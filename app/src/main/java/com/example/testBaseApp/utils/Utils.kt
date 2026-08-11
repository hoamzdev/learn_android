package com.example.testBaseApp.utils

import android.Manifest
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.testBaseApp.ui.obj.OfficeModel
import java.io.File

object Utils {

    private const val TAG = "Utils"

    // --- Permissions ---

    @JvmStatic
    fun requestStoragePermission(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val intents =
                listOf(
                    Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION").apply {
                    data = "package:${activity.packageName}".toUri()
                },
                    Intent("android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION"),
                    Intent("android.settings.APPLICATION_DETAILS_SETTINGS").apply {
                        data = "package:${activity.packageName}".toUri()
                    })

            for (intent in intents) {
                try {
                    if (activity.packageManager.resolveActivity(intent, 0) != null) {
                        activity.startActivityForResult(intent, 2296)
                        break
                    }
                } catch (_: Exception) {
                }
            }
        } else {
            ActivityCompat.requestPermissions(
                activity, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 2296
            )
        }

        val intents = listOf(
            android.content.Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION")
                .apply {
                    data = "package:${activity.packageName}".toUri()
                },
            android.content.Intent("android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION"),
            android.content.Intent("android.settings.APPLICATION_DETAILS_SETTINGS").apply {
                data = "package:${activity.packageName}".toUri()
            })

        for (intent in intents) {
            try {
                if (activity.packageManager.resolveActivity(intent, 0) != null) {
                    activity.startActivityForResult(intent, 2296)
                    break
                }
            } catch (_: Exception) {
            }
        }
    }

    @JvmStatic
    fun hasStoragePermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            ContextCompat.checkSelfPermission(
                context,
                "android.permission.READ_EXTERNAL_STORAGE"
            ) == 0 || ContextCompat.checkSelfPermission(
                context,
                "android.permission.WRITE_EXTERNAL_STORAGE"
            ) == 0
        }
    }

    @JvmStatic
    fun getExcelFiles(context: Context): ArrayList<OfficeModel> {
        val fileList = ArrayList<OfficeModel>()
        val contentUri: Uri = MediaStore.Files.getContentUri("external_primary")

        // Lấy thêm cột _ID để tạo Content Uri
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.MIME_TYPE
        )

        val selection =
            "mime_type LIKE 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' " +
                    "OR mime_type LIKE 'application/vnd.ms-excel' " +
                    "OR mime_type LIKE 'application/vnd.ms-excel.sheet.macroenabled.12' " +
                    "OR mime_type LIKE 'application/vnd.ms-excel.template.macroenabled.12' " +
                    "OR mime_type LIKE 'application/vnd.openxmlformats-officedocument.spreadsheetml.template'"

        try {
            context.contentResolver.query(
                contentUri,
                projection,
                selection,
                null,
                "${MediaStore.Files.FileColumns.DATE_ADDED} DESC"
            )?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val idIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns._ID)
                    val dataIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA)
                    val nameIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME)

                    do {
                        val id = if (idIndex != -1) cursor.getLong(idIndex) else -1L
                        val path = if (dataIndex != -1) cursor.getString(dataIndex) else null
                        val name = if (nameIndex != -1) cursor.getString(nameIndex) else null

                        if (!path.isNullOrEmpty() && !name.isNullOrEmpty()) {
                            val file = File(path)
                            if (file.exists() && file.length() > 0) {

                                // Tạo Content Uri chuẩn từ ID
                                val actualUri: Uri = if (id != -1L) {
                                    ContentUris.withAppendedId(contentUri, id)
                                } else {
                                    Uri.fromFile(file)
                                }

                                fileList.add(
                                    OfficeModel().apply {
                                        nameFile = file.name
                                        fileUri = actualUri.toString() // Lưu dạng "content://media/..." hoặc "file://..."
                                        length = file.length()
                                        lastModified = file.lastModified()
                                        isDirectory = file.isDirectory
                                    }
                                )
                            }
                        }
                    } while (cursor.moveToNext())
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error scanning Excel files", e)
        }

        return fileList
    }


    @JvmStatic
    fun isFilePendingOrTrashed(context: Context, uri: Uri): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return false

        try {
            context.contentResolver.query(
                uri,
                arrayOf("is_pending", "is_trashed"),
                null,
                null,
                null
            )?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val pendingIndex = cursor.getColumnIndex("is_pending")
                    val trashedIndex = cursor.getColumnIndex("is_trashed")

                    val isPending = if (pendingIndex != -1) cursor.getInt(pendingIndex) else 0
                    val isTrashed = if (trashedIndex != -1) cursor.getInt(trashedIndex) else 0

                    return isPending == 1 || isTrashed == 1
                }
            }
        } catch (_: Exception) {
        }

        return false
    }

}