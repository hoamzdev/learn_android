package com.example.data.remote.interceptor

import com.example.data.local.sharePres.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * @author hwa..
 */
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager,
) : Interceptor {
    //method tu dong chay khi co request di qua
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = tokenManager.getAccessToken()

        //gan them authorization va accessToken
        val request = chain.request().newBuilder().apply {
            if (accessToken.isNotBlank()) {
                header("Authorization", "Bearer $accessToken")
            }
        }.build()
        //cho request di qua
        return chain.proceed(request)
    }
}
