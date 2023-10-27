package com.golnegari.core.repository.impl

import com.golnegari.core.domain.base.DataResult
import com.golnegari.core.domain.base.DomainModelList
import com.golnegari.core.domain.model.Movie
import com.golnegari.core.domain.repository.MovieRepository
import com.golnegari.core.network.datasource.RemoteMovieDataSource
import javax.inject.Inject


class MovieRepositoryImpl @Inject constructor(private val remoteMovieDataSource: RemoteMovieDataSource) :
    MovieRepository {
    override suspend fun getPopularMovie(): DataResult<DomainModelList<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetail(movieId: String): DataResult<Movie> {
        TODO("Not yet implemented")
    }

}