package com.example.data.repository

import com.example.data.base.BaseRepository
import com.example.data.remote.api.UserApi
import com.example.data.remote.qualifier.PublicApi
import com.example.domain.entities.UserResponse
import com.example.domain.repository.UserRepository
import com.example.domain.utils.ApiResult
import javax.inject.Inject

/**
 * @author hwa..
 */
class UserRepositoryImpl @Inject constructor(
    @param:PublicApi private val userApi: UserApi,
) : UserRepository, BaseRepository() {

    override suspend fun getUser(): ApiResult<UserResponse> {
        return safeApiCall {
            val userResponse = userApi.getUser()
            userResponse
        }
    }
}