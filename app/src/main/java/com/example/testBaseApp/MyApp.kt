package com.example.testBaseApp

import android.app.Application
import com.aspose.cells.License
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