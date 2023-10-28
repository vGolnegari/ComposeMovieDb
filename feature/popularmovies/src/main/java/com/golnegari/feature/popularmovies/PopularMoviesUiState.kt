package com.golnegari.feature.popularmovies

import com.golnegari.common.base.UiModel
import com.golnegari.common.base.UiState
import com.golnegari.core.domain.model.Movie

data class PopularMoviesUiState(
    val movies: List<Movie> = emptyList()
) : UiModel
