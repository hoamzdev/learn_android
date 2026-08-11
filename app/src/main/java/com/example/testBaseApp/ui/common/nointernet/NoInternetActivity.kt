package com.example.testBaseApp.ui.common.nointernet

import android.content.Intent
import android.provider.Settings
import android.util.Log
import com.example.testBaseApp.base.BaseActivity
import com.example.testBaseApp.component.setOnSingleListener
import com.example.testBaseApp.databinding.ActivityNoInternetBinding
import com.example.testBaseApp.utils.CheckInternet

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