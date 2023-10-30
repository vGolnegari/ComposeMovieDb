package com.golnegari.core.testing.data

import androidx.paging.PagingData
import com.golnegari.core.domain.model.Genre
import com.golnegari.core.domain.model.Movie
import com.golnegari.core.domain.model.MovieDetail
import kotlinx.coroutines.flow.flowOf

val fakeMovieDetailInfo = MovieDetail(id = 3, originalTitle = "Harry Potter", backdropPath = null, posterPath = null,
    releaseDate = "2000/03/05", originalLanguage = "en", overview = "Harry Potter and Half blood Princess", voteAverage = 8.9, voteCount = 1330,
    genres = listOf(Genre("Science"))
)


val fakePopularMovieList = listOf(
    Movie(id = 1, title = "Harry Potter-3", posterPath = null),
    Movie(id = 2, title = "Harry Potter-4",posterPath = null))

val fakePagingSource = flowOf(PagingData.from(fakePopularMovieList))