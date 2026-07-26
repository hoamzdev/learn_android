package com.example.testBaseApp.ui.main

import com.example.testBaseApp.base.BaseActivity
import com.example.testBaseApp.component.setOnClickShrinkListener
import com.example.testBaseApp.databinding.ActivityMain3Binding

class MainActivity3 : BaseActivity<ActivityMain3Binding>(ActivityMain3Binding::inflate){

    override fun bindView() {
        viewBinding.tvNameCt.setName("Dinh Van Hoa")
        val name = viewBinding.tvNameCt.getName()
        log(name)
    }
}