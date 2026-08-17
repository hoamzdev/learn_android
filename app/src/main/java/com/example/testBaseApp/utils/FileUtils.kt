package com.example.testBaseApp.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.example.testBaseApp.ui.main.MainActivity6.Companion.INVALID_NAME
import kotlin.text.substring

object FileUtils {
    fun getNameFileFromUri(context: Context, uri: Uri?): String {
        if (uri == null) return INVALID_NAME
        var fileName: String? = ""
        if (uri.scheme == "content") {
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (nameIndex != -1) {
                        fileName = cursor.getString(nameIndex)
                    }
                }
            }
        }
        if (fileName == null) {
            fileName = uri.path.let {
                val cut = it?.lastIndexOf('/')//last index has /
                if (cut != null && cut >= 0) {
                    it.substring(cut + 1)//cat tu index + 1 den het file -> name file
                } else {
                    it
                }
            }
        }
        return fileName ?: INVALID_NAME
    }

    fun getEndFileName(nameFile : String) : String{
        val indexDotLast = nameFile.lastIndexOf('.')
        return if (indexDotLast != -1) {
            nameFile.substring(indexDotLast)
        }
        else ""
    }

}