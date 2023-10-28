package com.golnegari.core.domain.model

import com.golnegari.core.domain.base.BaseDomainModel

data class MovieDetail(
    val id: Int,
    val backdropPath: String?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val genres : List<Genre>,
) : BaseDomainModel
