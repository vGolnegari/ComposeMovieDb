package com.golnegari.core.domain.repository

import androidx.paging.PagingData
import com.golnegari.core.domain.base.DataResult
import com.golnegari.core.domain.base.DomainModelList
import com.golnegari.core.domain.model.Movie
import com.golnegari.core.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovieDetail(movieId: Int): DataResult<MovieDetail>
    fun syncPopularMovies(): Flow<PagingData<Movie>>
}