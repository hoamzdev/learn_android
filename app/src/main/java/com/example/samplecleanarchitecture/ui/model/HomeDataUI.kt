package com.example.samplecleanarchitecture.ui.model

/**
 * @author hwa..
 */

data class Banner(
    val name : String
)

sealed class HomeDataUI{
    data class BannerUI(val banners: List<Banner>) : HomeDataUI()
    data class DataItemUI(val title : String) : HomeDataUI()
}