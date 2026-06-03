package com.example.samplecleanarchitecture.ui.main

import androidx.activity.viewModels
import com.example.domain.entities.AuthRequest
import com.example.samplecleanarchitecture.base.BaseActivity
import com.example.samplecleanarchitecture.component.setOnSingleListener
import com.example.samplecleanarchitecture.databinding.ActivityMainBinding
import com.example.samplecleanarchitecture.ui.common.nointernet.NoInternetActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val viewModel: MainViewModel by viewModels()

    override fun bindView() {
        viewBinding.apply {
            btnLogin.setOnSingleListener { _ ->
                viewModel.login(
                    AuthRequest(
                        username = "emilys", password = "emilyspass"
                    )
                )
            }
            btnGet.setOnSingleListener {
                viewModel.getCurrentUser()
            }
        }
    }

    override fun bindViewModel() {
        observerData {
            viewModel.logoutAgain.collect { status ->
                if(status){
                    showActivity(NoInternetActivity::class.java)
                }
            }
        }
    }
}