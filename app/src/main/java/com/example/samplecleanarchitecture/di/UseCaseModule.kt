package com.example.samplecleanarchitecture.di

import com.example.domain.repository.CompanyRepository
import com.example.domain.repository.DepartmentLocalRepository
import com.example.domain.usecase.DepartmentLocalUseCase
import com.example.domain.usecase.GetDepartmentUseCase
import com.example.domain.usecase.GetEmployeeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetDepartmentUseCase(
        repository: CompanyRepository,
    ): GetDepartmentUseCase = GetDepartmentUseCase(repository)

    @Provides
    fun provideGetEmployeeUseCase(
        repository: CompanyRepository,
    ): GetEmployeeUseCase = GetEmployeeUseCase(repository)

    @Provides
    fun provideDepartmentLocalUseCase(
        repository: DepartmentLocalRepository,
    ): DepartmentLocalUseCase = DepartmentLocalUseCase(repository)
}
