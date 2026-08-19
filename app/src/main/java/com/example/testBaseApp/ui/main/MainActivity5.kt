package com.example.testBaseApp.ui.main

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.net.toUri
import com.example.testBaseApp.base.BaseActivity
import com.example.testBaseApp.databinding.ActivityMain5Binding
import com.example.testBaseApp.ui.adapter.FileExcelAdapter
import com.example.testBaseApp.utils.FileUtils
import com.example.testBaseApp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity5 : BaseActivity<ActivityMain5Binding>(ActivityMain5Binding::inflate) {
    private val adapter by lazy { FileExcelAdapter() }

    private fun onSendUri(uri: Uri) {
        val intent = Intent(this, MainActivity6::class.java).apply {
            data = uri
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
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

//    private fun uriToJson(uri: Uri?) {
//        lifecycleScope.launch(Dispatchers.IO) {
//            val fileName = uri?.path?.lowercase() ?: ""
//            try {
//                val jsonContent = if (fileName.endsWith(".csv")) {
//                    parseCsvToJson(uri)
//                } else {
//                    parseExcelToJson(uri)
//                }
//                //switch context to main thread
//                withContext(Dispatchers.Main) {
//                    val intent = Intent(this@MainActivity5, MainActivity6::class.java).apply {
//                        putExtra(KEY_JSON_FILE, jsonContent)
//                    }
//                    startActivity(intent)
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//    @SuppressLint("Recycle")
//    private fun parseCsvToJson(uri: Uri?): String {
//        uri?.let {
//            val inputStream = contentResolver.openInputStream(uri) ?: return "[]"
//            val reader = BufferedReader(InputStreamReader(inputStream))
//            val rowsObj = JSONObject()
//            var rowIndex = 0
//            reader.forEachLine { line ->
//                val cellsObj = JSONObject()
//                val tokens = line.split(",")
//                tokens.forEachIndexed { index, value ->
//                    val cellData = JSONObject().put("text", value.trim().replace("\"", ""))
//                    cellsObj.put("$index", cellData)
//                }
//                val rowData = JSONObject().put("cells", cellsObj)
//                rowsObj.put("$rowIndex", rowData)
//                rowIndex++
//            }
//            inputStream.close()
//            val sheetObj = JSONObject().apply {
//                put("name", "Sheet1")
//                put("rows", rowsObj)
//            }
//            return JSONArray().put(sheetObj).toString().replace("\\", "")
//        }
//        return "[]"
//    }
//
//    @SuppressLint("Recycle")
//    private fun parseExcelToJson(uri: Uri?): String {
//        uri ?: return "[]"
//
//        val inputStream = contentResolver.openInputStream(uri) ?: return "[]"
//
//        val workbook = WorkbookFactory.create(inputStream)
//        val sheet = workbook.getSheetAt(0)
//
//        val rowsObj = JSONObject()
//
//
//        for (row in sheet) {
//
//            val cellsObj = JSONObject()
//
//            val lastCell = row.lastCellNum.toInt()
//
//            for (col in 0 until lastCell) {
//
//                val cell = row.getCell(col)
//
//                val value = when (cell?.cellType) {
//                    CellType.STRING -> cell.stringCellValue
//                    CellType.NUMERIC -> cell.numericCellValue.toString()
//                    CellType.BOOLEAN -> cell.booleanCellValue.toString()
//                    CellType.FORMULA -> cell.cellFormula
//                    else -> ""
//                }
//
//                val cellObj = JSONObject()
//                cellObj.put("text", value)
//
//                cellsObj.put(col.toString(), cellObj)
//            }
//
//            val rowObj = JSONObject()
//            rowObj.put("cells", cellsObj)
//
//            rowsObj.put(row.rowNum.toString(), rowObj)
//        }
//
//        for (region in sheet.mergedRegions) {
//
//            val firstRow = region.firstRow
//            val firstCol = region.firstColumn
//
//            val rowSpan = region.lastRow - region.firstRow
//            val colSpan = region.lastColumn - region.firstColumn
//
//            val rowObj = rowsObj.optJSONObject(firstRow.toString()) ?: continue
//            val cellsObj = rowObj.optJSONObject("cells") ?: continue
//            val cellObj = cellsObj.optJSONObject(firstCol.toString()) ?: continue
//
//            cellObj.put(
//                "merge", JSONArray().put(rowSpan).put(colSpan)
//            )
//        }
//
//        workbook.close()
//        inputStream.close()
//
//        val sheetObj = JSONObject().apply {
//            put("name", sheet.sheetName)
//            put("rows", rowsObj)
//        }
//        Log.e("JSON", JSONArray().put(sheetObj).toString(2))
//        return JSONArray().put(sheetObj).toString()
//    }

    companion object {
        const val KEY_JSON_FILE = "key_json_file"
        const val INVALID_NAME = "Invalid name"
        const  val REQUEST_CODE_STORAGE = 2296
    }
}
