package com.golnegari.composemovie.di

import com.golnegari.core.network.di.MovieDbApiKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @MovieDbApiKey
    fun provideMovieDbApiKey(): String = "55957fcf3ba81b137f8fc01ac5a31fb5"

}