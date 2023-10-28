package com.golnegari.feature.moviedetail

import com.golnegari.common.base.BaseViewModel
import com.golnegari.common.base.UiState
import com.golnegari.core.domain.usecase.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val getMovieDetails: GetMovieDetailsUseCase) :
    BaseViewModel<MovieDetailUiModel, UiState<MovieDetailUiModel>, MovieDetailUiAction>(UiState.Loading()) {
    override fun reduceState(action: MovieDetailUiAction) {
        when (action) {
            is MovieDetailUiAction.LoadMovieDetail -> {
                collect(
                    dataResultFlow = getMovieDetails.invoke(movieId = action.movieId),
                    retryAction = action
                ) {
                    UiState.Success(MovieDetailUiModel(it.data))
                }
            }
        }
    }

}