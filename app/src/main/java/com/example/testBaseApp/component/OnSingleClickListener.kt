package com.example.testBaseApp.component

import android.content.Intent
import android.os.SystemClock
import android.view.View
import com.example.testBaseApp.ui.common.nointernet.NoInternetActivity
import com.example.testBaseApp.utils.CheckInternet

/**
 * @author hwa..
 */

fun View.setOnSingleListener(
    duration: Long = 300L,
    internet : Boolean = true,
    block: (View) -> Unit,
) {
    var lastClickTime = 0L
    setOnClickListener {
        val currentTime = SystemClock.elapsedRealtime()
        if(currentTime - lastClickTime >= duration){
            lastClickTime = currentTime
            if(!CheckInternet.haveNetworkConnection(context) && internet){
                val intent = Intent(context, NoInternetActivity::class.java)
                context.startActivity(intent)
            }
            else block(it)
        }
    }
}