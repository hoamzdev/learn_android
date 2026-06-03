package com.example.samplecleanarchitecture.ui.common.nointernet

import android.content.Intent
import android.provider.Settings
import android.util.Log
import com.example.samplecleanarchitecture.base.BaseActivity
import com.example.samplecleanarchitecture.component.setOnSingleListener
import com.example.samplecleanarchitecture.databinding.ActivityNoInternetBinding
import com.example.samplecleanarchitecture.ui.main.MainActivity
import com.example.samplecleanarchitecture.utils.CheckInternet

class NoInternetActivity :
    BaseActivity<ActivityNoInternetBinding>(ActivityNoInternetBinding::inflate) {

    override fun bindView() {
        viewBinding.btnRetry.setOnSingleListener {
            //no internet
            if(!CheckInternet.haveNetworkConnection(this)){
                val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                startActivity(intent)
            }
            else{
                val intent = Intent(this@NoInternetActivity, MainActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
            Log.e("TEST", "click")
        }
    }

    override fun onResume() {
        super.onResume()
        if(CheckInternet.haveNetworkConnection(this)){
            val intent = Intent(this@NoInternetActivity, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}