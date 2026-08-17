package com.example.testBaseApp.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import com.example.testBaseApp.base.BaseDialog
import com.example.testBaseApp.databinding.LayoutDialogAskSaveBinding

class AskSaveDialog(
    context: Context,
    val onClickSave :() -> Unit,
    val onClickExit :() -> Unit,
) : BaseDialog<LayoutDialogAskSaveBinding>(context) {

    override fun setBinding(layoutInflater: LayoutInflater): LayoutDialogAskSaveBinding {
        return LayoutDialogAskSaveBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.apply {
            btnExit.setOnClickListener {
                onClickExit()
                dismiss()
            }
            btnSave.setOnClickListener {
                onClickSave.invoke()
            }
        }
    }
}