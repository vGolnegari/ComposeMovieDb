package com.golnegari.core.network.di

import com.golnegari.core.network.datasource.impl.RemoteMovieDataSourceImpl
import com.golnegari.core.network.datasource.RemoteMovieDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RemoteDataSourceModule {

    @Binds
    fun bindRemoteMovieDataSourceImpl(impl: RemoteMovieDataSourceImpl): RemoteMovieDataSource
}