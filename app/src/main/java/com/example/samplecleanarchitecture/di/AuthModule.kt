package com.example.samplecleanarchitecture.di

import com.example.data.remote.api.AuthenticationApi
import com.example.data.remote.qualifier.AuthApi
import com.example.data.remote.qualifier.AuthClient
import com.example.data.remote.qualifier.AuthLogging
import com.example.data.remote.qualifier.AuthRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author hwa..
 */

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    private const val BASE_URL =
        "https://dummyjson.com/"

    @Provides
    @Singleton
    @AuthLogging
    fun provideLogging() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    @AuthClient
    fun provideAuthClient(
        @AuthLogging logging : HttpLoggingInterceptor
    ) : OkHttpClient{
        return OkHttpClient
            .Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    @AuthRetrofit
    fun provideAuthRetrofit(
        @AuthClient client : OkHttpClient
    ) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @AuthApi
    fun provideAuthApi(
        @AuthRetrofit retrofit: Retrofit
    ) : AuthenticationApi {
        return retrofit.create(AuthenticationApi::class.java)
    }
}