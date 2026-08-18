package com.example.testBaseApp.ui.main

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.example.testBaseApp.R
import com.example.testBaseApp.base.BaseActivity
import com.example.testBaseApp.databinding.ActivityMain6Binding
import com.example.testBaseApp.ui.adapter.PickColorAdapter
import com.example.testBaseApp.ui.adapter.TabSheetAdapter
import com.example.testBaseApp.ui.adapter.TabSheetAdapterTemp
import com.example.testBaseApp.ui.dialog.AskSaveDialog
import com.example.testBaseApp.utils.FileUtils
import com.example.testBaseApp.utils.invisible
import com.example.testBaseApp.utils.visible
import com.hwa.excelllll.SODoc
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


@AndroidEntryPoint
class MainActivity6 : BaseActivity<ActivityMain6Binding>(ActivityMain6Binding::inflate) {

    private val uri by lazy {
        intent.data ?: intent.getStringExtra(MainActivity5.KEY_JSON_FILE)?.toUri()
    }

    private val viewModel: MainViewModel by viewModels()
    private val tabAdapter: TabSheetAdapterTemp by lazy { TabSheetAdapterTemp() }
    private val pickColorAdapter: PickColorAdapter by lazy { PickColorAdapter() }
    private var askSaveDialog: AskSaveDialog? = null
    private lateinit var nameFile: String

    private var isEditMode : Boolean = false
    private var isShowPickColorTab : Boolean = false

    override val belowStatusBar: Boolean
        get() = false


    override fun initView() {
        nameFile = FileUtils.getNameFileFromUri(this, uri)
        viewBinding.tvNameFile.text = nameFile
        setupExcelEventListeners()
        setUpShowSheet()
        onClickListener()
        setupEditActions()
    }

    private fun setUpShowSheet(){
        if (viewModel.document != null) {
            viewBinding.viewExcel.bindExistingDocument(
                viewModel.document!!,
                viewModel.currentSheetIndex
            )
            setUpSheet(viewModel.document!!)
        } else {
            uri?.let { viewBinding.viewExcel.open(it) } ?: log("Không nhận được URI file Excel")
        }
        tabAdapter.setCurrentSheetClicked(viewModel.currentSheetIndex)
    }

    private fun setupExcelEventListeners() {
        viewBinding.viewExcel.apply {
            onDocumentError = { mes ->
                log("onDocumentError: $mes")
            }
            onDocumentReady = { doc ->
                viewModel.document = doc
                setUpSheet(doc)
                val currentSheetIndex = viewModel.currentSheetIndex
                showSheet(currentSheetIndex) { status ->
                    if (status != 0) {
                        log("Không mở được sheet $currentSheetIndex, status=$status")
                    }
                }
            }
            onSheetListChanged = { sheets ->
                tabAdapter.submitList(sheets)
            }
            onCellSelected = { selected ->
                viewModel.selectionState = selected
                handleSetUpEditBg(viewBinding.bottomEdit.btnTextBold, selected.isBold)
                handleSetUpEditBg(viewBinding.bottomEdit.btnTextItalic, selected.isItalic)
                handleSetUpEditBg(viewBinding.bottomEdit.btnTextUnder, selected.underLine)
                handleSetUpEditBg(viewBinding.bottomEdit.btnStriketrough, selected.isStrikeThrough)
                handleEnableDisableEditFontSize(selected.fontSize)
            }
        }
    }



    private fun onClickListener() {
        handleClickTabSheet()
        viewBinding.apply {
            tvRotate.setOnClickListener {
                switchOrientation()
            }
            tvEdit.setOnClickListener {
                isEditMode = !isEditMode
                handleToggleEditUI(isEditMode)
            }
        }
    }

    private fun setupEditActions() {
        viewBinding.bottomEdit.recyclerColor.adapter = pickColorAdapter
        pickColorAdapter.submitList(viewModel.listColorBackGround)
        pickColorAdapter.setOnPickColorCallBack(object : PickColorAdapter.PickColorCallBack {
            override fun onPickColor(color: String) {
                viewBinding.viewExcel.setFontColorCell(color)
                isShowPickColorTab = !isShowPickColorTab
                viewBinding.bottomEdit.textColorRecyclerView.visibility = View.GONE
            }
        })
        
        viewBinding.bottomEdit.apply {
            btnTextSizeDown.setOnClickListener { setTextSizeDown() }
            btnTextSizeUp.setOnClickListener { setTextSizeUp() }
            btnTextBold.setOnClickListener { setTextBold() }
            btnTextItalic.setOnClickListener { setTextItalic() }
            btnTextUnder.setOnClickListener { setTextUnder() }
            btnTextColor.setOnClickListener { 
                isShowPickColorTab = !isShowPickColorTab
                textColorRecyclerView.visibility = if(isShowPickColorTab) View.VISIBLE else View.GONE
            }
            btnStriketrough.setOnClickListener { setTextStrikeTrough() }
            btnCut.setOnClickListener { cutTextCell() }
            btnCopy.setOnClickListener { copyTextCell() }
            btnPaste.setOnClickListener { pasteTextCell() }
        }
    }

    private fun handleToggleEditUI(show: Boolean) {
        viewBinding.bottomEdit.root.visibility = if(show) View.VISIBLE else View.GONE
        if (!show) {
            isShowPickColorTab = false
            viewBinding.bottomEdit.textColorRecyclerView.visibility = View.GONE
        }
    }
    
    private fun handleSetUpEditBg(view : View, enable : Boolean){
        view.setBackgroundResource(if(enable) R.drawable.item_edit_bg else R.drawable.item_edit_bg_unselected)
    }
    
    fun pasteTextCell() = viewBinding.viewExcel.paste()
    fun copyTextCell() = viewBinding.viewExcel.copy()
    fun cutTextCell() = viewBinding.viewExcel.cut()
    fun setTextStrikeTrough() {
        val current = viewModel.selectionState?.isStrikeThrough ?: false
        val newState = !current
        viewBinding.viewExcel.setStrikeThroughCell(newState)
    }
    fun setTextUnder() {
        val current = viewModel.selectionState?.underLine ?: false
        val newState = !current
        viewBinding.viewExcel.setUnderlineCell(newState)
    }
    fun setTextItalic() {
        val current = viewModel.selectionState?.isItalic ?: false
        val newState = !current
        viewBinding.viewExcel.setItalicCell(newState)
    }
    fun setTextBold(){
        val current = viewModel.selectionState?.isBold ?: false
        val newState = !current
        viewBinding.viewExcel.setBoldCell(newState)
    }

    fun setTextSizeUp() {
        val currentFontSize = viewModel.selectionState?.fontSize ?: return
        val newSize = currentFontSize + 1
        viewModel.selectionState = viewModel.selectionState?.copy(fontSize = newSize)
        viewBinding.viewExcel.setFontSizeCell(newSize)
    }
    
    fun setTextSizeDown() {
        val currentFontSize = viewModel.selectionState?.fontSize ?: return
        val newSize = currentFontSize - 1
        viewModel.selectionState = viewModel.selectionState?.copy(fontSize = newSize)
        viewBinding.viewExcel.setFontSizeCell(newSize)
    }

    private fun setUpSheet(document: SODoc) {
        val sheets = document.sheetNames()
        if (sheets.size > 1) {
            viewBinding.rcTabs.visible()
        }
        viewBinding.rcTabs.adapter = tabAdapter
        tabAdapter.submitList(sheets)
    }

    private fun handleClickTabSheet() {
        tabAdapter.setOnClickTab { index ->
            viewModel.currentSheetIndex = index
            viewBinding.viewExcel.showSheet(index) { status ->
                if (status != 0) {
                    log("Không mở được sheet $index, status=$status")
                }
            }
        }
    }

    private fun switchOrientation() {
        val orientation = resources.configuration.orientation
        requestedOrientation = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    override fun onDestroy() {
        viewBinding.viewExcel.close()
        super.onDestroy()
    }

    override fun onFinish() {
        val doc = viewModel.document
        if (doc != null && doc.getHasBeenModified()) {
            askSaveDialog = AskSaveDialog(this, onClickSave = {
                saveDocument(doc)
                askSaveDialog?.dismiss()
                askSaveDialog = null
            }, onClickExit = {
                askSaveDialog = null
            })
            askSaveDialog?.show()
        } else {
            finish()
        }
    }

    private fun handleEnableDisableEditFontSize(fontSize : Double){
        if(fontSize > 6 && fontSize < 20){
            viewBinding.bottomEdit.apply {
                btnTextSizeDown.apply {
                    isEnabled = true
                    alpha = 1f
                }
                btnTextSizeUp.apply {
                    isEnabled = true
                    alpha = 1f
                }
            }
        }
        else if(fontSize < 6){
            viewBinding.bottomEdit.btnTextSizeDown.apply {
                isEnabled = false
                alpha = 0.4f
            }
        }
        else if(fontSize > 20){
            viewBinding.bottomEdit.btnTextSizeUp.apply {
                isEnabled = false
                alpha = 0.4f
            }
        }
    }

    private fun getFileName(name: String): String {
        val indexDotLast = name.lastIndexOf(".")
        return if (indexDotLast != -1) {
            name.substring(0, indexDotLast)
        } else {
            "${System.currentTimeMillis()}"
        }
    }

    private fun saveDocument(doc: SODoc) {
        viewBinding.progressBar.visible()
        viewBinding.viewExcel.commitCurrentEdit {
            val currentUri = uri ?: return@commitCurrentEdit
            val typeFile = FileUtils.getEndFileName(nameFile)
            val tempSaveFile = File(cacheDir, "saved_${System.currentTimeMillis()}$typeFile")

            doc.save(tempSaveFile.absolutePath) { result, extra ->
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        if (result == 0 && tempSaveFile.exists() && tempSaveFile.length() > 0) {
                            contentResolver.openOutputStream(currentUri, "rwt")?.use { output ->
                                tempSaveFile.inputStream().use { input ->
                                    input.copyTo(output)
                                }
                            }
                            withContext(Dispatchers.Main) {
                                viewBinding.progressBar.invisible()
                                toast("Lưu thành công")
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        withContext(Dispatchers.Main) {
                            viewBinding.progressBar.invisible()
                            toast("Lưu lỗi: ${e.message}")
                        }
                    } finally {
                        if (tempSaveFile.exists()) tempSaveFile.delete()
                    }
                }
            }
        }
    }

    private val launcherPdf = registerForActivityResult(
        ActivityResultContracts.CreateDocument("application/pdf")
    ) { uri ->
        if (uri != null) {
            saveAsPdf(viewModel.document!!, uri)
        }
    }

    private fun saveAsPdf(doc: SODoc?, uri: Uri) {
        if (doc == null) return
        viewBinding.progressBar.visible()
        viewBinding.viewExcel.commitCurrentEdit {
            val tempSaveFile = File(cacheDir, "temp_pdf_${System.currentTimeMillis()}.pdf")
            doc.savePdf(tempSaveFile.absolutePath, false) { result, extra ->
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        if (result == 0 && tempSaveFile.exists() && tempSaveFile.length() > 0) {
                            contentResolver.openOutputStream(uri, "rwt")?.use { output ->
                                tempSaveFile.inputStream().use { input ->
                                    input.copyTo(output)
                                }
                            }
                            withContext(Dispatchers.Main) {
                                viewBinding.progressBar.invisible()
                                toast("Xuất PDF thành công")
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        withContext(Dispatchers.Main) {
                            viewBinding.progressBar.invisible()
                            toast("Lỗi PDF: ${e.message}")
                        }
                    } finally {
                        if (tempSaveFile.exists()) tempSaveFile.delete()
                    }
                }
            }
        }
    }

    companion object {
        const val INVALID_NAME = "Invalid name"
    }
}
