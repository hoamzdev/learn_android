package com.example.testBaseApp.ui.main

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.activity.viewModels
import androidx.core.net.toUri
import com.example.testBaseApp.base.BaseActivity
import com.example.testBaseApp.databinding.ActivityMain6Binding
import com.example.testBaseApp.ui.adapter.PickColorAdapter
import com.example.testBaseApp.ui.adapter.TabSheetAdapter
import com.example.testBaseApp.utils.FileUtils
import com.example.testBaseApp.utils.visible
import com.hwa.excelllll.SODoc
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity6 : BaseActivity<ActivityMain6Binding>(ActivityMain6Binding::inflate) {

    private val uri by lazy {
        intent.data ?: intent.getStringExtra(MainActivity5.KEY_JSON_FILE)?.toUri()
    }

    private val viewModel: MainViewModel by viewModels()
    private val tabAdapter: TabSheetAdapter by lazy { TabSheetAdapter() }
    private val pickColorAdapter : PickColorAdapter by lazy { PickColorAdapter() }

    override val belowStatusBar: Boolean
        get() = false

    override fun initView() {
        val nameFile = FileUtils.getNameFileFromUri(this, uri)
        viewBinding.tvNameFile.text = nameFile
        
        setupExcelListeners()

        if (viewModel.document != null) {
            viewBinding.viewExcel.bindExistingDocument(viewModel.document!!, viewModel.currentSheetIndex)
            setUpSheet(viewModel.document!!)
        } else {
            uri?.let { viewBinding.viewExcel.open(it) } ?: log("Không nhận được URI file Excel")
        }

        onClickListener()
    }

    private fun setupExcelListeners() {
        viewBinding.viewExcel.apply {
            onDocumentError = { mes ->
                log("onDocumentError: $mes")
            }
            onDocumentReady = { doc ->
                viewModel.document = doc
                setUpSheet(doc)
                log("onDocumentReady")
            }
            onSheetListChanged = { sheets ->
                tabAdapter.submitList(sheets)
            }
            onCellSelected = { selected ->
                viewModel.selectionState = selected
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
                handleEditSheet()
            }
        }
    }


    //fill tab sheet
    private fun setUpSheet(document: SODoc) {
        val sheets = document.sheetNames()
//        log("Sheets (${sheets.size}): $sheets")
        sheets.forEach { log(it) }
        if (sheets.size > 1) {
            viewBinding.rcTabs.visible()
        }
        viewBinding.rcTabs.adapter = tabAdapter
        tabAdapter.submitList(sheets)
    }

    //handle tab click
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

    //switch orientation
    private fun switchOrientation() {
        val orientation = resources.configuration.orientation
        requestedOrientation = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // -> land
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            //portrait
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    private fun handleEditSheet() {
        val ok = viewBinding.viewExcel.setBackgroundColorCell("#F21B1B")
        log(ok.toString())
    }


    override fun onDestroy() {
        viewBinding.viewExcel.close()
        super.onDestroy()
    }

    companion object {
        const val INVALID_NAME = "Invalid name"
    }
}
