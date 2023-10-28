package com.golnegari.core.repository

import com.golnegari.core.domain.model.Movie
import com.golnegari.core.network.model.MovieJsonModel

fun MovieJsonModel.toDomainMovie() : Movie = Movie(id = id ?:  -1,
    title = title ?: "",
    posterPath = backdropPath?.let { "https://image.tmdb.org/t/p/w500/${it}"})