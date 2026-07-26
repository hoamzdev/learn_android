package com.example.testBaseApp.di

import com.example.domain.repository.AuthRepository
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.GetCurrentUserUseCase
import com.example.domain.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideLoginUseCase(
        repository: AuthRepository,
    ) = LoginUseCase(repository)

    @Provides
    fun provideGetCurrentUser(
        repository: UserRepository,
    ): GetCurrentUserUseCase = GetCurrentUserUseCase(repository)
}
