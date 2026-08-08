package com.example.testBaseApp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.testBaseApp.base.BaseListAdapter
import com.example.testBaseApp.base.BaseViewHolder
import com.example.testBaseApp.component.setOnClickShrinkListener
import com.example.testBaseApp.databinding.ItemFileExcelBinding
import com.example.testBaseApp.ui.ExcelFileItem
import com.example.testBaseApp.ui.obj.OfficeModel

data class FileExcelItem(
    val name: String,
    val uri: String
)

class FileExcelAdapter :
    BaseListAdapter<OfficeModel, ItemFileExcelBinding, FileExcelAdapter.FileExcelViewHolder>(
        diffCallback
    ) {

    private var onClick : (OfficeModel) -> Unit = {}
    fun setOnClick(block : (OfficeModel) -> Unit){
        this.onClick = block
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): FileExcelViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return FileExcelViewHolder(ItemFileExcelBinding.inflate(inflate, parent, false))
    }

    inner class FileExcelViewHolder(override val binding: ItemFileExcelBinding) :
        BaseViewHolder<OfficeModel, ItemFileExcelBinding>(binding) {

        init {
            binding.regionView.setOnClickShrinkListener {
                val item = getItem(bindingAdapterPosition)
                onClick.invoke(item)
            }
        }

        override fun onBindData(data: OfficeModel) {
            super.onBindData(data)
            binding.apply {
                tvName.text = data.nameFile
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<OfficeModel>() {
            override fun areItemsTheSame(
                oldItem: OfficeModel, newItem: OfficeModel
            ): Boolean {
                return oldItem.absolutePath == newItem.absolutePath
            }

            override fun areContentsTheSame(
                oldItem: OfficeModel, newItem: OfficeModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}