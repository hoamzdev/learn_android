package com.example.testBaseApp.base

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.core.graphics.drawable.toDrawable
import androidx.viewbinding.ViewBinding

abstract class BasePopupWindow<VB : ViewBinding>(
    context: Context,
    bindingInflater: (LayoutInflater) -> VB
) : PopupWindow(context) {

    protected val binding: VB

    init {
        val inflater = LayoutInflater.from(context)
        binding = bindingInflater(inflater)
        contentView = binding.root
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        isFocusable = true

        setBackground()
    }

    private fun setBackground() {
        setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
    }

}