package com.example.testBaseApp.ui.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.testBaseApp.base.BaseListAdapter
import com.example.testBaseApp.base.BaseViewHolder
import com.example.testBaseApp.databinding.ItemColorPickEditBinding
import androidx.core.graphics.toColorInt

class PickColorAdapter :
    BaseListAdapter<String, ItemColorPickEditBinding, PickColorAdapter.PickColorViewHolder>(
        diffCallback
    ) {

    private var pickColorCallBack : PickColorCallBack? = null

    private fun setOnPickColorCallBack(callBack: PickColorCallBack) {
        this.pickColorCallBack = callBack
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PickColorViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return PickColorViewHolder(ItemColorPickEditBinding.inflate(inflate))
    }

    override fun onBindViewHolder(holder: PickColorViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
    }

    inner class PickColorViewHolder(override val binding: ItemColorPickEditBinding) :
        BaseViewHolder<String, ItemColorPickEditBinding>(binding) {

        init {
            binding.vPickColor.setOnClickListener {
                pickColorCallBack?.onPickColor(data ?: "#FFFFFFFF")
            }
        }

        override fun onBindData(data: String) {
            super.onBindData(data)
            val colorInt = data.toColorInt()
            binding.vPickColor.backgroundTintList = ColorStateList.valueOf(colorInt)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    private interface PickColorCallBack{
        fun onPickColor(color: String)
    }
}