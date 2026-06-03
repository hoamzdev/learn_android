package com.example.domain.usecase

import com.example.domain.repository.UserRepository

/**
 * @author hwa..
 */
class GetCurrentUserUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke() = userRepository.getUser()
}