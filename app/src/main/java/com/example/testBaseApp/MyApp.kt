package com.example.testBaseApp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * @author hwa..
 */

@HiltAndroidApp
class MyApp : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}