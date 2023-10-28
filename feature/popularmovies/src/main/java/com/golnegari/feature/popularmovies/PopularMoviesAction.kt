package com.golnegari.feature.popularmovies

import com.golnegari.common.base.UiAction

sealed interface PopularMoviesAction : UiAction {

    data object GetPopularMovies : PopularMoviesAction

    data class OnGotoMovieDetail(val movieId: Int) : PopularMoviesAction


}