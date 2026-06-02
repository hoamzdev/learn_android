package com.example.samplecleanarchitecture.di

import com.example.data.api.CompanyApi
import com.example.data.local.dao.DepartmentDao
import com.example.data.repository.CompanyRepositoryImpl
import com.example.data.repository.DepartmentRepositoryImpl
import com.example.domain.repository.CompanyRepository
import com.example.domain.repository.DepartmentLocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideCompanyRepository(api: CompanyApi): CompanyRepository {
        return CompanyRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideDepartmentLocalRepository(dao: DepartmentDao): DepartmentLocalRepository {
        return DepartmentRepositoryImpl(dao)
    }
}
