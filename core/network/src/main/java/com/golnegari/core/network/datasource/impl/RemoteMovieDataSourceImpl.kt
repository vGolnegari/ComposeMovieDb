package com.golnegari.core.network.datasource.impl

import com.golnegari.core.network.base.ApiResult
import com.golnegari.core.network.base.getApiResult
import com.golnegari.core.network.datasource.RemoteMovieDataSource
import com.golnegari.core.network.model.MovieDetailJsonModel
import com.golnegari.core.network.model.MovieJsonModel
import com.golnegari.core.network.model.MovieListJson
import com.golnegari.core.network.util.NetworkConnectionUtil
import com.golnegari.core.network.retrofit.apiServices.MovieApiService
import javax.inject.Inject

class RemoteMovieDataSourceImpl @Inject constructor(
    private val networkConnectionUtil: NetworkConnectionUtil,
    private val movieApiService: MovieApiService
) :
    RemoteMovieDataSource {

    override suspend fun fetchPopularMovieList(page: Int): MovieListJson {
        return movieApiService.getPopularMovies(page)
    }

    override suspend fun fetchMovieDetail(movieId: Int): ApiResult<MovieDetailJsonModel> {
        return suspend { movieApiService.getMovieDetailInfo(movieId) }.getApiResult(
            networkConnectionUtil
        )
    }
}