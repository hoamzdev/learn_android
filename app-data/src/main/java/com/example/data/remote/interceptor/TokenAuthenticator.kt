package com.example.data.remote.interceptor

import com.example.data.local.sharePres.TokenManager
import com.example.data.remote.api.AuthenticationApi
import com.example.data.remote.qualifier.AuthApi
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author hwa..
 */

@Singleton
class TokenAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
    @param:AuthApi
    private val authApi: AuthenticationApi
    ) : Authenticator {

    //tu dong kich hoa khi server tra ve 401 Unauthorized(token het han)
    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = tokenManager.getRefreshToken()//lay ra refresh token dang luu trong sharePrefs
        if(refreshToken.isBlank()) {
            logout()
            return null
        }
        if(hasAttemptedRefresh(response)) {
            logout()
            return null
        }
        return try{
            //ep chay dong bo de lay authResponse
            val refreshResponse = runBlocking {
                authApi.refreshToken(refreshToken)
            }

            tokenManager.saveToken(refreshResponse.accessToken)
            tokenManager.saveRefreshToken(refreshResponse.refreshToken)

            //gan token moi vao request
            response.request.newBuilder()
                .header(
                    "Authorization",
                    "Bearer ${refreshResponse.accessToken}"
                ).build()
        }catch (e : Exception){
            tokenManager.clear()
            logout()
            null
        }
    }

    //refresh > 1 lan -> logout
    private fun hasAttemptedRefresh(
        response: Response
    ) : Boolean{
        return response.priorResponse != null
    }

    private fun logout(){
        tokenManager.clear()
        tokenManager.logoutEvent()
    }

}