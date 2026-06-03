package com.example.domain.repository

import com.example.domain.entities.AuthRequest
import com.example.domain.entities.AuthResponse
import com.example.domain.entities.UserResponse
import com.example.domain.utils.ApiResult

/**
 * @author hwa..
 */
interface AuthRepository {
    suspend fun login(request: AuthRequest): ApiResult<AuthResponse>
}