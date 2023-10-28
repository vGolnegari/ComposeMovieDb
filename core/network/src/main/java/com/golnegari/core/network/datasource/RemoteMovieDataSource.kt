package com.golnegari.core.network.datasource

import com.golnegari.core.network.base.ApiResult
import com.golnegari.core.network.model.MovieDetailJsonModel
import com.golnegari.core.network.model.MovieJsonModel
import com.golnegari.core.network.model.MovieListJson


interface RemoteMovieDataSource {
    suspend fun fetchPopularMovieList(page : Int) : MovieListJson
    suspend fun fetchMovieDetail(movieId : Int) : ApiResult<MovieDetailJsonModel>
}