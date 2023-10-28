package com.golnegari.core.repository.impl

import com.golnegari.core.domain.base.DataResult
import com.golnegari.core.domain.base.DataResultErrorType
import com.golnegari.core.domain.base.DomainModelList
import com.golnegari.core.domain.model.Movie
import com.golnegari.core.domain.repository.MovieRepository
import com.golnegari.core.network.base.ApiResult
import com.golnegari.core.network.datasource.RemoteMovieDataSource
import com.golnegari.core.repository.toDomainMovie
import javax.inject.Inject


class MovieRepositoryImpl @Inject constructor(private val remoteMovieDataSource: RemoteMovieDataSource) :
    MovieRepository {
    override suspend fun getPopularMovie(): DataResult<DomainModelList<Movie>> {
        val movieListApiResult = remoteMovieDataSource.fetchPopularMovieList()
        return if (movieListApiResult is ApiResult.Success) {
            val domainMovieList = DomainModelList<Movie>()
            val list = movieListApiResult.data.result?.map {it.toDomainMovie()

            } ?: emptyList()
            if (list.isNotEmpty()) {
                domainMovieList.addAll(list)
            }
            DataResult.Success(data = domainMovieList)
        } else {
            DataResult.Error(type = DataResultErrorType.NETWORK_GENERIC_ERROR)
        }
    }

    override suspend fun getMovieDetail(movieId: String): DataResult<Movie> {
        TODO("Not yet implemented")
    }

}