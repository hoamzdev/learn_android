package com.example.samplecleanarchitecture.ui.blog

import androidx.lifecycle.lifecycleScope
import com.example.samplecleanarchitecture.base.BaseActivity
import com.example.samplecleanarchitecture.databinding.ActivityBlogBinding
import com.example.samplecleanarchitecture.ui.common.nointernet.NoInternetActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BlogActivity : BaseActivity<ActivityBlogBinding>(ActivityBlogBinding::inflate) {

    override fun bindView() {
        lifecycleScope.launch {
            delay(3000L)
            showActivity(NoInternetActivity::class.java, null)
        }
    }
}