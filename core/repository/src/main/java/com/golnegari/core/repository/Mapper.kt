package com.golnegari.core.repository

import com.golnegari.core.domain.model.Movie
import com.golnegari.core.domain.model.MovieDetail
import com.golnegari.core.network.model.MovieDetailJsonModel
import com.golnegari.core.network.model.MovieJsonModel

fun MovieJsonModel.toDomainMovie() : Movie = Movie(id = id ?:  -1,
    title = title ?: "",
    posterPath = backdropPath?.let { "https://image.tmdb.org/t/p/w500/${it}"})

fun MovieDetailJsonModel.toDomainModel() : MovieDetail = MovieDetail(id = id ?: -1 ,
    poster_path = poster_path?.let { "https://image.tmdb.org/t/p/w500/${it}" } ,
    backdrop_path = backdrop_path?.let { "https://image.tmdb.org/t/p/w500/${it}" },
    original_title = original_title ,
    original_language = original_language ,
    vote_average = vote_average ?: 0.0 ,
    vote_count = vote_count ?: 0,
    release_date = release_date ,
    overview = overview )