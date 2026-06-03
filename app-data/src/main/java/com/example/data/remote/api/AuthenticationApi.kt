package com.example.data.remote.api

import com.example.domain.entities.AuthRequest
import com.example.domain.entities.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * @author hwa..
 */
interface AuthenticationApi {
    @POST("auth/login")
    suspend fun login(
        @Body request: AuthRequest,
    ): AuthResponse

    @POST("auth/refresh")
    suspend fun refreshToken(
        @Query("refreshToken") refreshToken: String,
    ): AuthResponse
}