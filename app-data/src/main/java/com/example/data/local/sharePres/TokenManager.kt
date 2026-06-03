package com.example.data.local.sharePres

import android.content.Context
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author hwa..
 */

@Singleton
class TokenManager @Inject constructor(
    context: Context,
) {
    private val prefs = context.getSharedPreferences("auth_prefs",Context.MODE_PRIVATE)

    fun saveToken(token : String) {
        prefs.edit {
            putString(ACCESS_TOKEN,token)
        }
    }

    fun getAccessToken() : String{
        return prefs.getString(ACCESS_TOKEN, "") ?: ""
    }

    fun saveRefreshToken(refreshToken : String){
        prefs.edit {
            putString(REFRESH_TOKEN,refreshToken)
        }
    }

    fun getRefreshToken() : String{
        return prefs.getString(REFRESH_TOKEN,"") ?: ""
    }

    //clear all token
    fun clear(){
        prefs.edit { clear() }
    }

    private var _forceLogout = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val forceLogout = _forceLogout.asSharedFlow()

    fun logoutEvent(){
        _forceLogout.tryEmit(Unit)
    }

    companion object{
        private const val ACCESS_TOKEN = "access_token"
        private const val REFRESH_TOKEN = "refresh_token"
    }
}