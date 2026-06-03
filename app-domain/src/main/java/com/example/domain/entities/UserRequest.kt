package com.example.domain.entities

/**
 * @author hwa..
 */

data class AuthRequest(
    val username: String,
    val password: String,
)

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
)

data class UserResponse(
    val id: Long,
    val username: String,
    val email: String,
)