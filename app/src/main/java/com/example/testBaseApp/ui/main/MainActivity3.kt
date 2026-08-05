package com.example.testBaseApp.ui.main

import android.graphics.Color
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.example.testBaseApp.R
import com.example.testBaseApp.base.BaseActivity
import com.example.testBaseApp.component.setOnClickShrinkListener
import com.example.testBaseApp.databinding.ActivityMain3Binding
import com.google.android.flexbox.FlexboxLayout

class MainActivity3 : BaseActivity<ActivityMain3Binding>(ActivityMain3Binding::inflate){

    private val wordOptions = listOf(
        "Option 1",
        "Option 2",
        "Option 3",
        "Option 4",
        "Option 5",
        "Option 6",
        "Option 6",
        "Option 7",
        "Option 8",
        "Option 9",
    )

    override fun initView() {
        setupWords()
    }

    private fun setupWords() {
        viewBinding.flbOption.removeAllViews()
        wordOptions.forEach { word ->
            val wordView = createWordTextView(word)
            wordView.setOnClickShrinkListener{
                moveWord(it)
            }
            viewBinding.flbOption.addView(wordView)
        }
    }

    private fun createWordTextView(word : String) : TextView{
        return TextView(this).apply {
            text = word
            setTextColor(Color.BLACK)
            setBackgroundResource(R.drawable.bg_edt_12_selected)
            setPadding(5,5,5,5)
            val params = FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(8, 8, 8, 8)
            }
            layoutParams = params
        }
    }

    private fun moveWord(view : View){
        val changeBound = ChangeBounds().apply {
            duration = 200
            interpolator = OvershootInterpolator(1.3f)
        }
        TransitionManager.beginDelayedTransition(
            viewBinding.root,
            changeBound
        )
        if(view.parent == viewBinding.flbOption){
            viewBinding.flbOption.removeView(view)
            viewBinding.flbAnswer.addView(view)
        }else{
            viewBinding.flbAnswer.removeView(view)
            viewBinding.flbOption.addView(view)
        }
    }
}