package com.hwa.excelllll.excel

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File
import java.io.FileOutputStream

object ExcelFileAccess {

    fun copyToCache(context: Context, uri: Uri): String {
        val extension = extensionOf(context, uri)
        val fileName = "artifex_${System.currentTimeMillis()}$extension"
        val target = File(context.cacheDir, fileName)

        context.contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(target).use { output ->
                input.copyTo(output)
            }
        } ?: error("Unable to open input URI: $uri")

        return target.absolutePath
    }

    fun extensionOf(uri: Uri): String {
        return extensionFromName(
            uri.lastPathSegment
            ?.substringAfterLast('/', "")
            ?.substringAfterLast(':', "")
        )
    }

    fun extensionOf(context: Context, uri: Uri): String {
        val displayName = runCatching {
            context.contentResolver.query(
                uri,
                arrayOf(OpenableColumns.DISPLAY_NAME),
                null,
                null,
                null
            )?.use { cursor ->
                if (!cursor.moveToFirst()) return@use null
                val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (index >= 0) cursor.getString(index) else null
            }
        }.getOrNull()

        return extensionFromName(displayName)
            .ifBlank { extensionOf(uri) }
    }

    private fun extensionFromName(name: String?): String {
        val extension = name
            ?.substringAfterLast('.', "")
            ?.takeIf { it.isNotBlank() }

        return if (extension == null) "" else ".$extension"
    }

    fun isExcel(uri: Uri): Boolean {
        val extension = extensionOf(uri).lowercase()
        return extension == ".xls" || extension == ".xlsx"
    }

    fun isExcel(context: Context, uri: Uri): Boolean {
        val extension = extensionOf(context, uri).lowercase()
        return extension == ".xls" || extension == ".xlsx"
    }

    fun deleteCachedFile(path: String?) {
        if (path.isNullOrBlank()) return
        runCatching { File(path).delete() }
    }
}
