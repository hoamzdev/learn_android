package com.example.domain.repository

import com.example.domain.entities.UserResponse
import com.example.domain.utils.ApiResult

/**
 * @author hwa..
 */
interface UserRepository {
    suspend fun getUser(): ApiResult<UserResponse>
}