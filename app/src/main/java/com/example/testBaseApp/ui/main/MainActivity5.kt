package com.example.testBaseApp.ui.main

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.net.toUri
import com.example.testBaseApp.base.BaseActivity
import com.example.testBaseApp.databinding.ActivityMain5Binding
import com.example.testBaseApp.ui.adapter.FileExcelAdapter
import com.example.testBaseApp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity5 : BaseActivity<ActivityMain5Binding>(ActivityMain5Binding::inflate) {
    private val adapter by lazy { FileExcelAdapter() }
    private fun onSendUri(uri: Uri) {

        startActivity(intent)
    }

    override fun initView() {
        setUpRecView()
        checkAndRequestPermission()
    }

    private fun checkAndRequestPermission(){
        if(Utils.hasStoragePermission(this)){
            loadExcelFiles()
        }
        else{
            Utils.requestStoragePermission(this)
        }
    }

    // 4. Nhận kết quả sau khi người dùng đồng ý/từ chối cấp quyền
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_STORAGE) {
            if (Utils.hasStoragePermission(this)) {
                loadExcelFiles()
            } else {
                Toast.makeText(this, "Bạn chưa cấp quyền đọc bộ nhớ!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_STORAGE) {
            if (Utils.hasStoragePermission(this)) {
                loadExcelFiles()
            } else {
                Toast.makeText(this, "Từ chối cấp quyền!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadExcelFiles(){
        val listFiles = Utils.getExcelFiles(this)
        adapter.submitList(listFiles)
    }

    private fun setUpRecView(){
        viewBinding.rcViewFile.adapter = adapter
        adapter.setOnClick { file ->
            onSendUri(file.fileUri!!.toUri())
        }
    }

    companion object {
        const val KEY_JSON_FILE = "key_json_file"
        const val INVALID_NAME = "Invalid name"
        const  val REQUEST_CODE_STORAGE = 2296
    }
}