package com.golnegari.core.network.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiKeyInterceptor

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LoggingInterceptor

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MovieDbApiKey
