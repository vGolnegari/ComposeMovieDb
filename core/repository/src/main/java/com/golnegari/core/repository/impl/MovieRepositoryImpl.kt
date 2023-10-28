package com.golnegari.core.repository.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.golnegari.core.domain.base.DataResult
import com.golnegari.core.domain.base.DataResultErrorType
import com.golnegari.core.domain.base.DomainModelList
import com.golnegari.core.domain.model.Movie
import com.golnegari.core.domain.model.MovieDetail
import com.golnegari.core.domain.repository.MovieRepository
import com.golnegari.core.network.base.ApiResult
import com.golnegari.core.network.datasource.RemoteMovieDataSource
import com.golnegari.core.repository.paging.MoviePagingSource
import com.golnegari.core.repository.toDomainModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(private val remoteMovieDataSource: RemoteMovieDataSource) :
    MovieRepository {

    override suspend fun getMovieDetail(movieId: Int): DataResult<MovieDetail> {
        val movieDetailResult = remoteMovieDataSource.fetchMovieDetail(movieId)
        return if (movieDetailResult is ApiResult.Success) {
            DataResult.Success(movieDetailResult.data.toDomainModel())
        } else {
            DataResult.Error(type = DataResultErrorType.NETWORK_GENERIC_ERROR)
        }
    }

    override fun syncPopularMovies(): Flow<PagingData<Movie>> =
        Pager(config = PagingConfig(pageSize = 20, prefetchDistance = 4), pagingSourceFactory = {
            MoviePagingSource(remoteMovieDataSource)
        }).flow
}