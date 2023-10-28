package com.golnegari.feature.popularmovies

import androidx.lifecycle.ViewModel
import com.golnegari.common.base.BaseViewModel
import com.golnegari.common.base.UiState
import com.golnegari.common.base.navigation.NavRoutes
import com.golnegari.core.domain.usecase.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(private val getPopularMovies: GetPopularMoviesUseCase) :
    BaseViewModel<PopularMoviesUiState,UiState<PopularMoviesUiState>,PopularMoviesAction>(UiState.Loading()) {
    override fun reduceState(action: PopularMoviesAction) {
        when(action) {
            is PopularMoviesAction.GetPopularMovies -> {
                collect(dataResultFlow = getPopularMovies.invoke(), retryAction = action) { successData ->
                    UiState.Success(PopularMoviesUiState(movies = successData.data))
                }
            }

            is PopularMoviesAction.OnGotoMovieDetail -> {
                submitState(state = UiState.Navigation(NavRoutes.MovieDetailNav.navigateToMovieDetail(movieId = action.movieId)))
            }
        }
    }
}