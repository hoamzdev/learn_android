package com.example.data.repository

import com.example.data.base.BaseRepository
import com.example.data.local.sharePres.TokenManager
import com.example.data.remote.api.AuthenticationApi
import com.example.data.remote.qualifier.AuthApi
import com.example.domain.entities.AuthRequest
import com.example.domain.entities.AuthResponse
import com.example.domain.repository.AuthRepository
import com.example.domain.utils.ApiResult
import javax.inject.Inject

/**
 * @author hwa..
 */
class AuthRepositoryImpl @Inject constructor(
    @param:AuthApi
    private val authApi : AuthenticationApi,
    private val tokenManager: TokenManager
) : AuthRepository, BaseRepository(){

    override suspend fun login(request: AuthRequest): ApiResult<AuthResponse> {
       return safeApiCall {
           //flow success
           val authResponse = authApi.login(request)

           //save to sharePrefs
           tokenManager.saveToken(authResponse.accessToken)
           tokenManager.saveRefreshToken(authResponse.refreshToken)
           authResponse//return value
       }
    }

}