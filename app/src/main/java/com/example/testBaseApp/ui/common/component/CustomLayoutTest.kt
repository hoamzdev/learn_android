package com.example.testBaseApp.ui.common.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.testBaseApp.databinding.CustomLayoutTestBinding

class CustomLayoutTest @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    deffStyle : Int = 0
) : ConstraintLayout(context,attributeSet, deffStyle){

    private var binding : CustomLayoutTestBinding =
        CustomLayoutTestBinding.inflate(LayoutInflater.from(context), this, true)

    fun setName(name : String){
        this.binding.tvName.text = name
    }

    fun getName() = this.binding.tvName.text.toString()

}