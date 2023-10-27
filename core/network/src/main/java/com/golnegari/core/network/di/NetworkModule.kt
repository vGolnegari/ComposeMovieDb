package com.golnegari.core.network.di

import com.golnegari.core.network.BuildConfig
import com.golnegari.core.network.retrofit.ApiKeyHeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @LoggingInterceptor
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }


    @ApiKeyInterceptor
    @Singleton
    @Provides
    fun provideApiKeyInterceptor(@MovieDbApiKey movieDbApiKey: String): Interceptor = ApiKeyHeaderInterceptor(apiKey = movieDbApiKey)


    @Singleton
    @Provides
    fun provideHttpClient(
        @LoggingInterceptor loggingInterceptor: Interceptor,
        @ApiKeyInterceptor apiKeyInterceptor: Interceptor
    ): Call.Factory {
        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

}