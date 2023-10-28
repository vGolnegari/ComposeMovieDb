package com.golnegari.feature.popularmovies

import androidx.paging.PagingData
import com.golnegari.common.base.UiModel
import com.golnegari.common.base.UiState
import com.golnegari.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class PopularMoviesUiState(
    val movies: List<Movie> = emptyList(),
    val pageableMovies : Flow<PagingData<Movie>> = flowOf()
) : UiModel
