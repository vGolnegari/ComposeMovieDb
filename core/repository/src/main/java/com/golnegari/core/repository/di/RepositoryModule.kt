package com.golnegari.core.repository.di

import com.golnegari.core.domain.repository.MovieRepository
import com.golnegari.core.network.datasource.RemoteMovieDataSource
import com.golnegari.core.repository.impl.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideMovieRepository(remoteDataSource: RemoteMovieDataSource): MovieRepository =
        MovieRepositoryImpl(remoteMovieDataSource = remoteDataSource)
}