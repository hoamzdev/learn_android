package com.example.testBaseApp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.testBaseApp.base.BaseListAdapter
import com.example.testBaseApp.base.BaseViewHolder
import com.example.testBaseApp.databinding.ItemTabBinding

class TabSheetAdapter :
    BaseListAdapter<String, ItemTabBinding, TabSheetAdapter.TabViewHolder>(diffCallback) {

    private var onClick : (Int) -> Unit = {}
    fun setOnClickTab(block : (Int) -> Unit){
        this.onClick = block
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): TabViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ItemTabBinding.inflate(inflate, parent, false)
        return TabViewHolder(binding)
    }

    inner class TabViewHolder(override val binding: ItemTabBinding) :
        BaseViewHolder<String, ItemTabBinding>(binding) {

        init {
            binding.regionTab.setOnClickListener {
                val position = bindingAdapterPosition
                onClick.invoke(position)
            }
        }

        override fun onBindData(data: String) {
            super.onBindData(data)
            binding.tvTabs.text = data
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
