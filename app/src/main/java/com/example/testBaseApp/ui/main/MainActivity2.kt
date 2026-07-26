package com.example.testBaseApp.ui.main

import com.example.testBaseApp.base.BaseActivity
import com.example.testBaseApp.component.setOnClickShrinkListener
import com.example.testBaseApp.databinding.ActivityMain2Binding
import com.example.testBaseApp.ui.common.component.ContentBottomSheet

class MainActivity2 : BaseActivity<ActivityMain2Binding>(ActivityMain2Binding::inflate){

    override fun initView() {

    }

    override fun bindView() {
        viewBinding.apply {
            btnShowSheet.setOnClickShrinkListener {
                val sheet = ContentBottomSheet()
                sheet.show(supportFragmentManager, ContentBottomSheet.TAG)
            }
        }
    }
}