package com.golnegari.feature.moviedetail

import com.golnegari.common.base.UiAction

sealed interface MovieDetailUiAction : UiAction{
    data class LoadMovieDetail(val movieId : Int) : MovieDetailUiAction
}