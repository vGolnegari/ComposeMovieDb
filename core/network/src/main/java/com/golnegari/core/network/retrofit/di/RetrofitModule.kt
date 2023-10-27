package com.golnegari.core.network.retrofit.di

import com.golnegari.core.network.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(json: Json, httpClient: Call.Factory): Retrofit =
        Retrofit.Builder().callFactory(httpClient)
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()


}