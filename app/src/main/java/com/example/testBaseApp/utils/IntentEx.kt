package com.example.testBaseApp.utils

import android.content.Intent
import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.Keep
import androidx.core.os.BundleCompat

@Keep
inline fun <reified T : Parcelable> Bundle.getParcel(key: String): T? {
    return if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        BundleCompat.getParcelable(this, key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getParcelable(key)
    }
}

inline fun <reified T> Intent.getParcelable(key: String): T? {
    return if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        extras?.let { BundleCompat.getParcelable(it, key, T::class.java) }
    } else {
        @Suppress("DEPRECATION")
        getParcelableExtra(key)
    }
}