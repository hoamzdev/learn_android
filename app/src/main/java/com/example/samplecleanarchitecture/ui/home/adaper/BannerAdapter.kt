package com.example.samplecleanarchitecture.ui.home.adaper

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.samplecleanarchitecture.base.BaseListAdapter
import com.example.samplecleanarchitecture.base.BaseViewHolder
import com.example.samplecleanarchitecture.databinding.ItemBannerBinding
import com.example.samplecleanarchitecture.ui.model.Banner
import jp.wasabeef.blurry.Blurry

/**
 * @author hwa..
 */

class BannerAdapter(
    private val context: Context,
) : BaseListAdapter<Banner, ItemBannerBinding, BaseViewHolder<Banner, ItemBannerBinding>>(
    diffCallBack
) {

    private var onClickBanner: (Banner) -> Unit = {}
    fun setOnClickBanner(onClickBanner: (Banner) -> Unit) {
        this.onClickBanner = onClickBanner
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<Banner, ItemBannerBinding> {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ItemBannerBinding.inflate(inflate, parent, false)
        return BannerViewHolder(binding)
    }

    inner class BannerViewHolder(override val binding: ItemBannerBinding) :
        BaseViewHolder<Banner, ItemBannerBinding>(binding) {
        override fun onBindData(data: Banner) {
            super.onBindData(data)
            //set data to ui
            binding.apply {
                ivItem.post {
                    Blurry.with(context).radius(5).sampling(2).async().capture(binding.ivItem)
                        .into(binding.ivItem)
                }
                tvNameItem.text = data.name
                cardItem.setOnClickListener {
                    onClickBanner.invoke(data)
                }
            }
        }
    }

    companion object {
        private val diffCallBack = object : DiffUtil.ItemCallback<Banner>() {
            override fun areItemsTheSame(
                old: Banner,
                new: Banner,
            ): Boolean {
                return old.name == new.name
            }

            override fun areContentsTheSame(
                old: Banner,
                new: Banner,
            ): Boolean {
                return old == new
            }
        }
    }

}