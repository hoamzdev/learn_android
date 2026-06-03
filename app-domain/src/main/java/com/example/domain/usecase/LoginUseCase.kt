package com.example.domain.usecase

import com.example.domain.entities.AuthRequest
import com.example.domain.repository.AuthRepository

/**
 * @author hwa..
 */
class LoginUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(authRequest: AuthRequest) = authRepository.login(authRequest)
}