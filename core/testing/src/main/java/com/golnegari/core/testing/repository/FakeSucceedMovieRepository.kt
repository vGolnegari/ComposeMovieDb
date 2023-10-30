package com.golnegari.core.testing.repository

import androidx.paging.PagingData
import com.golnegari.core.domain.base.DataResult
import com.golnegari.core.domain.model.Genre
import com.golnegari.core.domain.model.Movie
import com.golnegari.core.domain.model.MovieDetail
import com.golnegari.core.domain.repository.MovieRepository
import com.golnegari.core.testing.data.fakeMovieDetailInfo
import com.golnegari.core.testing.data.fakePagingSource
import com.golnegari.core.testing.data.fakePopularMovieList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeSucceedMovieRepository : MovieRepository {

    override suspend fun getMovieDetail(movieId: Int): DataResult<MovieDetail> {
        return DataResult.Success(fakeMovieDetailInfo)
    }

    override fun syncPopularMovies(): Flow<PagingData<Movie>> {
        return fakePagingSource
    }
}