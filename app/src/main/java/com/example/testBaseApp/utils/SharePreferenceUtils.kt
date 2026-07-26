package com.example.testBaseApp.utils

import android.content.Context
import android.content.res.Configuration
import android.graphics.ColorMatrix
import androidx.core.content.edit

import java.util.Locale

object SharePreferenceUtils {
    private const val PREF_NAME = "App_SharePreference"

    private val Context.sharedPreferences
            get() = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    //use
    /*
    context.sharedPreferences.edit{}
     */

    /*lưu các biến kiểu Int*/
    fun setInt(context: Context, nameString: String, limitNumber: Int) {
        val pre = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        pre.edit {
            putInt(nameString, limitNumber)
        }
    }

    fun getInt(context: Context, nameString: String, defaultValue: Int = 0): Int {
        val pre = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return pre.getInt(nameString, defaultValue)
    }

    /*lưu các biến kiểu String*/
    fun setString(context: Context, nameString: String, url: String) {
        val pre = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        pre.edit {
            putString(nameString, url)
        }
    }

    fun getString(context: Context, nameString: String): String? {
        val pre = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return pre.getString(nameString, null)
    }

    fun getString(context: Context, key: String, defValue: String = ""): String {
        val pre = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return pre.getString(key, defValue) ?: defValue
    }

    /*lưu các biến kiểu Long*/
    fun setLong(context: Context, nameString: String, long: Long) {
        val pre = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        pre.edit {
            putLong(nameString, long)
        }
    }

    fun getLong(context: Context, nameString: String): Long {
        val pre = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return pre.getLong(nameString, 0L)
    }

    /*lưu các biến kiểu Boolean*/
    fun setBoolean(context: Context, nameString: String, boolean: Boolean) {
        val pre = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        pre.edit {
            putBoolean(nameString, boolean)
        }
    }

    fun getBoolean(context: Context, nameString: String, defaultValue: Boolean = false): Boolean {
        val pre = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return pre.getBoolean(nameString, defaultValue)
    }

    fun saveDataEngine(
        context: Context,
        intValue: Int,
        stringValue: String
    ) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putInt("KEY_INT", intValue)
            putString("KEY_STRING", stringValue)
            apply()
        }
    }

    fun getData(context: Context): Pair<Int, String> {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val intValue = sharedPreferences.getInt("KEY_INT", -1)
        val stringValue = sharedPreferences.getString("KEY_STRING", "") ?: "Google"
        return Pair(intValue, stringValue)
    }

    /*lưu các biến kiểu Float*/
    fun setFloat(context: Context, nameString: String, limitNumber: Float) {
        val pre = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        pre.edit {
            putFloat(nameString, limitNumber)
        }
    }

    fun getFloat(context: Context, nameString: String, defaultValue: Float = 0f): Float {
        val pre = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return pre.getFloat(nameString, defaultValue)
    }
}
