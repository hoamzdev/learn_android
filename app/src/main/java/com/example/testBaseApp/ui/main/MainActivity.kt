package com.example.testBaseApp.ui.main

import android.view.View
import android.widget.LinearLayout
import com.example.testBaseApp.base.BaseActivity
import com.example.testBaseApp.component.setOnClickShrinkListener
import com.example.testBaseApp.component.setOnSingleListener
import com.example.testBaseApp.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private lateinit var sheetBehavior : BottomSheetBehavior<LinearLayout>

    override fun initView() {
        sheetBehavior = BottomSheetBehavior.from(viewBinding.sheetContent)
        sheetBehavior.isHideable = true
        sheetBehavior.peekHeight = 0
        sheetBehavior.isDraggable = true
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun bindView() {
        viewBinding.apply {
            btnSort.setOnClickShrinkListener { _ ->
                if(sheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN){
                    sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
                else{
                    sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
            }

            btnHide.setOnSingleListener {
                sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }

            btnNavToScreen2.setOnClickShrinkListener {
                showActivity(MainActivity2::class.java,null)
            }

        }

        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(p0: View, newState: Int) {
                when(newState){
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        log("STATE_HIDDEN")
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        log("STATE_EXPANDED")
                    }
                    else -> {}
                }
            }
            override fun onSlide(p0: View, p1: Float) {

            }
        })

    }
}