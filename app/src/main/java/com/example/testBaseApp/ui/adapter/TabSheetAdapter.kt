package com.example.testBaseApp.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testBaseApp.R
import com.example.testBaseApp.base.BaseMultiViewAdapter
import com.example.testBaseApp.databinding.ItemAddBlankPageBinding
import com.example.testBaseApp.databinding.ItemTabBinding
import com.example.testBaseApp.ui.obj.TabSheetItem

class TabSheetAdapter : BaseMultiViewAdapter<TabSheetItem>(diffCallBack) {

    private var onClick: (Int) -> Unit = {}
    private var currentClicked = 0
    fun setOnClickTab(block: (Int) -> Unit) {
        this.onClick = block
    }

    fun setCurrentSheetClicked(position: Int) {
        currentClicked = position
    }

    private var onClickAddSheet : () -> Unit = {}
    fun setOnClickAddSheet(block: () -> Unit) {
        this.onClickAddSheet = block
    }

    override fun getViewType(item: TabSheetItem): Int {
        return when(item){
            is TabSheetItem.TabSheetItemContent -> TAB_SHEET
            is TabSheetItem.AddSheet -> ADD_SHEET
        }
    }

    override fun bindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: TabSheetItem,
        position: Int
    ) {
        if(position  < currentList.size && position >= 0){
            val item = getItem(position)
            when(holder){
                is TabSheetViewHolder -> {
                    if(item is TabSheetItem.TabSheetItemContent){
                        holder.bind(item)
                    }
                }
                is AddSheetViewHolder -> {
                    if(item is TabSheetItem.AddSheet){
                        holder.onClickAdd()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return when(viewType){
            TAB_SHEET -> {
                val binding = ItemTabBinding.inflate(inflate, parent, false)
                TabSheetViewHolder(binding)
            }
            ADD_SHEET -> {
                val binding = ItemAddBlankPageBinding.inflate(inflate, parent, false)
                AddSheetViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    inner class TabSheetViewHolder(
        private val binding : ItemTabBinding
    ) : RecyclerView.ViewHolder(binding.root){

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

        fun bind(item : TabSheetItem.TabSheetItemContent){
            val name = item.name
            binding.tvTabs.text = name

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

    inner class AddSheetViewHolder(
        private val binding : ItemAddBlankPageBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onClickAdd(){
            binding.root.setOnClickListener {
                onClickAddSheet.invoke()
            }
        }
    }

    companion object {
        const val TAB_SHEET = 0
        const val ADD_SHEET = 1
        private val diffCallBack = object : DiffUtil.ItemCallback<TabSheetItem>() {
            override fun areItemsTheSame(
                oldItem: TabSheetItem,
                newItem: TabSheetItem
            ): Boolean {
                if (oldItem is TabSheetItem.TabSheetItemContent && newItem is TabSheetItem.TabSheetItemContent) {
                    return oldItem.name == newItem.name
                }
                return true
            }

            override fun areContentsTheSame(
                oldItem: TabSheetItem,
                newItem: TabSheetItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}