package com.example.samplecleanarchitecture.ui.home

import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplecleanarchitecture.base.BaseActivity
import com.example.samplecleanarchitecture.databinding.ActivityHomeBinding
import com.example.samplecleanarchitecture.ui.blog.BlogActivity
import com.example.samplecleanarchitecture.ui.home.adaper.HomeAdapter
import com.example.samplecleanarchitecture.ui.model.Banner
import com.example.samplecleanarchitecture.ui.model.HomeDataUI

class HomeActivity : BaseActivity<ActivityHomeBinding>(ActivityHomeBinding::inflate) {

    private val adapter by lazy { HomeAdapter(this) }

    override fun bindView() {
        viewBinding.apply {
            rcViewMulti.adapter = adapter
            rcViewMulti.layoutManager = LinearLayoutManager(this@HomeActivity)
        }
    }

    override fun bindViewModel() {
        adapter.submitList(mockData())

        adapter.setOnClickItem { data ->
            Log.e("HOME", data)
            showActivity(BlogActivity::class.java, null)
        }
    }

    fun mockData(): List<HomeDataUI> {
        return mutableListOf<HomeDataUI>().apply {
            add(
                HomeDataUI.BannerUI(
                    banners = listOf(
                        Banner(name = "Banner Quảng Cáo 1"),
                        Banner(name = "Banner Sự Kiện 2"),
                        Banner(name = "Banner Khuyến Mãi 3")
                    )
                )
            )

            for (i in 1..19) {
                add(HomeDataUI.DataItemUI(title = "Sản phẩm / Bài viết số $i"))
            }
        }
    }

}