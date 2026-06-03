package com.example.data.remote.api

import com.example.domain.entities.UserResponse
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * @author hwa..
 */
interface UserApi {
    @GET("auth/me")
    suspend fun getUser(): UserResponse
}