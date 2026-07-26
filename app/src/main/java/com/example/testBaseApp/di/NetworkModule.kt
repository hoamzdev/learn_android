package com.example.testBaseApp.di

import com.example.data.remote.api.UserApi
import com.example.data.remote.interceptor.AuthInterceptor
import com.example.data.remote.interceptor.TokenAuthenticator
import com.example.data.remote.qualifier.PublicApi
import com.example.data.remote.qualifier.PublicClient
import com.example.data.remote.qualifier.PublicLogging
import com.example.data.remote.qualifier.PublicRetrofit
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
object NetworkModule {

    private const val BASE_URL =
        "https://dummyjson.com/"

    @Provides
    @Singleton
    @PublicLogging
    fun provideLogging() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    @PublicClient
    fun providePublicClient(
        authInterceptor: AuthInterceptor,
        tokenAuthentication: TokenAuthenticator,
        @PublicLogging logging : HttpLoggingInterceptor
    ) : OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logging)
            .authenticator(tokenAuthentication)
            .build()
    }

    @Provides
    @Singleton
    @PublicRetrofit
    fun providePublicRetrofit(
        @PublicClient client : OkHttpClient
    ) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @PublicApi
    fun providePublicApi(
        @PublicRetrofit retrofit : Retrofit
    ) : UserApi{
        return retrofit.create(UserApi::class.java)
    }
}