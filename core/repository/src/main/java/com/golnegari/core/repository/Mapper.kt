package com.golnegari.core.repository

import com.golnegari.core.domain.model.Genre
import com.golnegari.core.domain.model.Movie
import com.golnegari.core.domain.model.MovieDetail
import com.golnegari.core.network.model.GenreJsonModel
import com.golnegari.core.network.model.MovieDetailJsonModel
import com.golnegari.core.network.model.MovieJsonModel

fun MovieJsonModel.toDomainMovie(): Movie = Movie(id = id ?: -1,
    title = title ?: "",
    posterPath = backdropPath?.let { "https://image.tmdb.org/t/p/w500/${it}" })

fun MovieDetailJsonModel.toDomainModel(): MovieDetail = MovieDetail(
    id = id ?: -1,
    posterPath = poster_path?.let { "https://image.tmdb.org/t/p/w500/${it}" },
    backdropPath = backdrop_path?.let { "https://image.tmdb.org/t/p/w500/${it}" },
    originalTitle = original_title,
    originalLanguage = original_language,
    voteAverage = vote_average ?: 0.0,
    voteCount = vote_count ?: 0,
    releaseDate = release_date,
    overview = overview,
    genres = genres?.filter { it.name != null }?.map { it.toDomainModel() } ?: emptyList(),
)

fun GenreJsonModel.toDomainModel(): Genre = Genre(name = name ?: "")
