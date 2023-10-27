package com.golnegari.core.network.retrofit.apiServices

import com.golnegari.core.network.model.MovieDetailJsonModel
import com.golnegari.core.network.model.MovieListJson
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): MovieListJson

    @GET("movie/{movie_id}")
    suspend fun getMovieDetailInfo(@Path("movie_id") movieId : Int) : MovieDetailJsonModel


}