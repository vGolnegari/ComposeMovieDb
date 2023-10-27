package com.golnegari.core.domain.repository

import com.golnegari.core.domain.base.DataResult
import com.golnegari.core.domain.base.DomainModelList
import com.golnegari.core.domain.model.Movie

interface MovieRepository {
    suspend fun getPopularMovie(): DataResult<DomainModelList<Movie>>
    suspend fun getMovieDetail(movieId: String): DataResult<Movie>
}