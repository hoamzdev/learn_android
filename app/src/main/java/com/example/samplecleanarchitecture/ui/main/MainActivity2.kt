package com.example.samplecleanarchitecture.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.samplecleanarchitecture.R
import com.example.samplecleanarchitecture.base.BaseActivity
import com.example.samplecleanarchitecture.component.setOnClickShrinkListener
import com.example.samplecleanarchitecture.databinding.ActivityMain2Binding
import com.example.samplecleanarchitecture.ui.common.component.ContentBottomSheet

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