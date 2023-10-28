package com.golnegari.feature.popularmovies

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.golnegari.common.base.BaseViewModel
import com.golnegari.common.base.UiState
import com.golnegari.common.base.navigation.NavRoutes
import com.golnegari.core.domain.usecase.SyncPopularMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(private val syncPopularMovies: SyncPopularMovieUseCase) :
    BaseViewModel<PopularMoviesUiState, UiState<PopularMoviesUiState>, PopularMoviesAction>(UiState.Loading()) {
    override fun reduceState(action: PopularMoviesAction) {
        when (action) {
            is PopularMoviesAction.GetPopularMovies -> {
                val syncedPopularMovie = syncPopularMovies.invoke().cachedIn(viewModelScope)
                submitState(state = UiState.Success(PopularMoviesUiState(pageableMovies = syncedPopularMovie)))
            }

            is PopularMoviesAction.OnGotoMovieDetail -> {
                submitState(
                    state = UiState.Navigation(
                        NavRoutes.MovieDetailNav.navigateToMovieDetail(
                            movieId = action.movieId
                        )
                    )
                )
            }
        }
    }
}