package com.example.testBaseApp.ui.main

import com.example.testBaseApp.base.BaseActivity
import com.example.testBaseApp.databinding.ActivityMain6Binding

class MainActivity6 : BaseActivity<ActivityMain6Binding>(ActivityMain6Binding::inflate) {

    private val uri by lazy {
        intent.getStringExtra(MainActivity5.KEY_JSON_FILE)
    }

    override fun initView() {
        log(uri.toString())
    }
}