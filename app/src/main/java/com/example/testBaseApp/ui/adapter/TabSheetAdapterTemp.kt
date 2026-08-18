package com.example.testBaseApp.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testBaseApp.R
import com.example.testBaseApp.base.BaseListAdapter
import com.example.testBaseApp.base.BaseViewHolder
import com.example.testBaseApp.databinding.ItemTabBinding

class TabSheetAdapterTemp :
    BaseListAdapter<String, ItemTabBinding, TabSheetAdapterTemp.TabViewHolder>(diffCallback) {

    private var onClick: (Int) -> Unit = {}
    private var currentClicked = 0
    fun setOnClickTab(block: (Int) -> Unit) {
        this.onClick = block
    }

    fun setCurrentSheetClicked(position: Int) {
        currentClicked = position
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): TabViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ItemTabBinding.inflate(inflate, parent, false)
        return TabViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TabViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBindData(item)
    }

    inner class TabViewHolder(override val binding: ItemTabBinding) :
        BaseViewHolder<String, ItemTabBinding>(binding) {

        init {
            binding.regionTab.setOnClickListener {
                val position = bindingAdapterPosition
                if (position == RecyclerView.NO_POSITION || position == currentClicked) return@setOnClickListener
                val preClicked = currentClicked
                currentClicked = position
                notifyItemChanged(preClicked)
                notifyItemChanged(currentClicked)
                onClick.invoke(position)
            }
        }

        @SuppressLint("SetTextI18n")
        override fun onBindData(data: String) {
            super.onBindData(data)
            binding.tvTabs.text = data

            val isClicked = (currentClicked == bindingAdapterPosition)
            binding.apply {
                tvTabs.setTextColor(if (isClicked) Color.BLACK else Color.WHITE)
                if (isClicked) {
                    regionTab.setBackgroundResource(R.drawable.bg_12)
                } else {
                    regionTab.setBackgroundResource(R.drawable.bg_12_unclick)
                }
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}
