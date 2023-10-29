package com.golnegari.core.testing.data

import com.golnegari.core.domain.model.Genre
import com.golnegari.core.domain.model.MovieDetail

val fakeMovieDetailInfo = MovieDetail(id = 3, originalTitle = "Harry Potter", backdropPath = null, posterPath = null,
    releaseDate = "2000/03/05", originalLanguage = "en", overview = "Harry Potter and Half blood Princess", voteAverage = 8.9, voteCount = 1330,
    genres = listOf(Genre("Science"))
)