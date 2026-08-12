package com.example.testBaseApp.ui.main

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.net.toUri
import com.example.testBaseApp.base.BaseActivity
import com.example.testBaseApp.databinding.ActivityMain6Binding

class MainActivity6 : BaseActivity<ActivityMain6Binding>(ActivityMain6Binding::inflate) {

    private val uri by lazy {
        intent.data ?: intent.getStringExtra(MainActivity5.KEY_JSON_FILE)?.toUri()
    }

    override val belowStatusBar: Boolean
        get() = false

    override fun initView() {
        val nameFile = getNameFileFromUri(this, uri)
        viewBinding.tvNameFile.text = nameFile
        viewBinding.viewExcel.apply {
            onDocumentError = { mes ->
                log("onDocumentError: $mes")
            }
            onDocumentReady = {
                log("onDocumentReady")
            }
            uri?.let(::open) ?: log("Không nhận được URI file Excel")
        }
    }

    override fun onDestroy() {
        viewBinding.viewExcel.close()
        super.onDestroy()
    }

    private fun getNameFileFromUri(context: Context, uri: Uri?): String {
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

    companion object {
        const val INVALID_NAME = "Invalid name"
    }
}
