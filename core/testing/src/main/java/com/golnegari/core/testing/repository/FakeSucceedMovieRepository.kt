package com.golnegari.core.testing.repository

import androidx.paging.PagingData
import com.golnegari.core.domain.base.DataResult
import com.golnegari.core.domain.model.Genre
import com.golnegari.core.domain.model.Movie
import com.golnegari.core.domain.model.MovieDetail
import com.golnegari.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class FakeSucceedMovieRepository : MovieRepository {

    val fakeMovieDetailInfo = MovieDetail(id = 3, originalTitle = "Harry Potter", backdropPath = null, posterPath = null,
        releaseDate = "2000/03/05", originalLanguage = "en", overview = "Harry Potter and Half blood Princess", voteAverage = 8.9, voteCount = 1330,
        genres = listOf(Genre("Science"))
    )

    override suspend fun getMovieDetail(movieId: Int): DataResult<MovieDetail> {
        return DataResult.Success(fakeMovieDetailInfo)
    }

    override fun syncPopularMovies(): Flow<PagingData<Movie>> {
        TODO("Not yet implemented")
    }
}