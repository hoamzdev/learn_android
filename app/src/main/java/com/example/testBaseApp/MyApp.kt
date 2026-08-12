package com.example.testBaseApp

import android.app.Application
import com.hwa.excelllll.core.DocumentEngine
import dagger.hilt.android.HiltAndroidApp

/**
 * @author hwa..
 */

@HiltAndroidApp
class MyApp : Application(){
    override fun onCreate() {
        super.onCreate()
        DocumentEngine.initialize(this)
    }
}
