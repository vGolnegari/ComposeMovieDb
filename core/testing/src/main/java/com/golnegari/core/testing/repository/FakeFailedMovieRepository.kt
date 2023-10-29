package com.golnegari.core.testing.repository

import androidx.paging.PagingData
import com.golnegari.core.domain.base.DataResult
import com.golnegari.core.domain.base.DataResultErrorType
import com.golnegari.core.domain.model.Movie
import com.golnegari.core.domain.model.MovieDetail
import com.golnegari.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class FakeFailedMovieRepository : MovieRepository {
    override suspend fun getMovieDetail(movieId: Int): DataResult<MovieDetail> {
        return DataResult.Error(type = DataResultErrorType.NETWORK_GENERIC_ERROR)
    }

    override fun syncPopularMovies(): Flow<PagingData<Movie>> {
        TODO("Not yet implemented")
    }
}