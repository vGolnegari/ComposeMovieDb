package com.golnegari.core.domain.model

import com.golnegari.core.domain.base.BaseDomainModel

data class MovieDetail(
    val id: Int,
    val backdrop_path: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val poster_path: String?,
    val release_date: String?,
    val vote_average: Double,
    val vote_count: Int
) : BaseDomainModel
