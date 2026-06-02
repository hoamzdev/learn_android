package com.example.samplecleanarchitecture.ui.home.adaper

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.samplecleanarchitecture.base.BaseMultiViewAdapter
import com.example.samplecleanarchitecture.base.BaseViewHolder
import com.example.samplecleanarchitecture.component.setOnClickShrinkListener
import com.example.samplecleanarchitecture.databinding.ItemDataBinding
import com.example.samplecleanarchitecture.databinding.ItemListBinding
import com.example.samplecleanarchitecture.ui.model.HomeDataUI

/**
 * @author hwa..
 */
class HomeAdapter(
    private val context: Context,
) : BaseMultiViewAdapter<HomeDataUI>(
    diffCallback
) {

    private var onClickItem: (String) -> Unit = {}
    fun setOnClickItem(block: (String) -> Unit) {
        this.onClickItem = block
    }

    //check type data and receive type holder
    override fun getViewType(item: HomeDataUI): Int {
        return when (item) {
            is HomeDataUI.BannerUI -> TYPE_BANNER
            else -> TYPE_ITEM
        }
    }

    //check type data and call method
    override fun bindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: HomeDataUI,
        position: Int,
    ) {
        if (position < currentList.size && position >= 0) {
            val item = getItem(position)
            when (holder) {
                is ListBannerViewHolder if item is HomeDataUI.BannerUI -> {
                    holder.bind(item)
                }

                is ItemViewHolder if item is HomeDataUI.DataItemUI -> {
                    holder.bind(item)
                }
            }
        }
    }

    //create class view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_BANNER -> {
                val binding = ItemListBinding.inflate(inflate, parent, false)
                ListBannerViewHolder(binding)
            }

            else -> {
                val binding = ItemDataBinding.inflate(inflate, parent, false)
                ItemViewHolder(binding)
            }
        }
    }

    inner class ListBannerViewHolder(binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val bannerAdapter by lazy { BannerAdapter(context) }

        init {
            binding.rcListBanner.apply {
                adapter = this@ListBannerViewHolder.bannerAdapter
                isNestedScrollingEnabled = false
                itemAnimator = null
            }
        }

        fun bind(item: HomeDataUI.BannerUI) {
            bannerAdapter.submitList(item.banners)
            bannerAdapter.setOnClickBanner { banner ->
                onClickItem.invoke(banner.name)
            }
        }
    }

    inner class ItemViewHolder(private val binding: ItemDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeDataUI.DataItemUI) {
            binding.apply {
                tvTitle.text = item.title
                cardItem.setOnClickShrinkListener {
                    onClickItem.invoke(item.title)
                }
            }
        }
    }


    companion object {
        private const val TYPE_BANNER = 0
        private const val TYPE_ITEM = 1

        private val diffCallback = object : DiffUtil.ItemCallback<HomeDataUI>() {
            override fun areItemsTheSame(
                oldItem: HomeDataUI,
                newItem: HomeDataUI,
            ): Boolean {
                return when (oldItem) {
                    is HomeDataUI.DataItemUI if newItem is HomeDataUI.DataItemUI -> true
                    is HomeDataUI.BannerUI if newItem is HomeDataUI.BannerUI -> true
                    else -> false
                }
            }

            override fun areContentsTheSame(
                oldItem: HomeDataUI,
                newItem: HomeDataUI,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}