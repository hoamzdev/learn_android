package com.example.data.remote.qualifier

import javax.inject.Qualifier

/**
 * @author hwa..
 */

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PublicClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PublicRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PublicApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthLogging

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PublicLogging