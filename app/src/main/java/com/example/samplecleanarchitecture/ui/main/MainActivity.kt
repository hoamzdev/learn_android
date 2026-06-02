package com.example.samplecleanarchitecture.ui.main

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.samplecleanarchitecture.R
import com.example.samplecleanarchitecture.base.BaseActivity
import com.example.samplecleanarchitecture.component.setOnSingleListener
import com.example.samplecleanarchitecture.databinding.ActivityMainBinding
import com.example.samplecleanarchitecture.ui.model.BottomNavUI
import com.example.samplecleanarchitecture.utils.setTint


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    val bottomNav = mutableListOf<BottomNavUI>()

    override fun initView() {
        viewBinding.bottomNavBar.apply {
            bottomNav.add(BottomNavUI(ivHome, tvTitleHome))
            bottomNav.add(BottomNavUI(ivBlog, tvTitleBlog))
            bottomNav.add(BottomNavUI(ivGarden, tvTitleGarden))
            bottomNav.add(BottomNavUI(ivHistory, tvTitleHistory))
        }
    }

    override fun bindView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        onNavigation(navController)
    }

    private fun onNavigation(navController: NavController) {
        viewBinding.bottomNavBar.apply {
            ivHome.setTint(R.color.color_06350D)//default
            tvTitleHome.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.color_06350D))
            btnHome.setOnSingleListener {
                uiTabOnClicked(ivHome, tvTitleHome)
                navController.navigate(R.id.homeFragment)
            }
            btnBlog.setOnSingleListener {
                uiTabOnClicked(ivBlog, tvTitleBlog)
                navController.navigate(R.id.blogFragment)
            }
            btnGarden.setOnSingleListener {
                uiTabOnClicked(ivGarden, tvTitleGarden)
                navController.navigate(R.id.gardenFragment)
            }
            btnHistory.setOnSingleListener {
                uiTabOnClicked(ivHistory, tvTitleHistory)
                navController.navigate(R.id.historyFragment)
            }
        }
    }

    private fun uiTabOnClicked(
        icon: AppCompatImageView,
        title: TextView,
    ) {
        icon.setTint(R.color.color_06350D)
        title.setTextColor(ContextCompat.getColor(this, R.color.color_06350D))
        val resetClicked = { v: View, title: TextView ->
            bottomNav.filter { (ic, tt) ->
                ic != v && tt != title
            }.forEach { (icon, title) ->
                run {
                    icon.setTint(R.color.color_909A92)
                    title.setTextColor(ContextCompat.getColor(this, R.color.color_909A92))
                }
            }
        }
        resetClicked(icon, title)
    }
}


