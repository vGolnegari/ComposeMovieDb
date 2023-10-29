package com.golnegari.feature.moviedetail

import com.golnegari.common.base.UiState
import com.golnegari.core.domain.base.DataResult
import com.golnegari.core.domain.model.MovieDetail
import com.golnegari.core.domain.usecase.GetMovieDetailsUseCase
import com.golnegari.core.testing.repository.FakeSucceedMovieRepository
import com.golnegari.core.testing.rules.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailUnitTest {

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase
    private val movieRepository = FakeSucceedMovieRepository()
    private lateinit var movieDetailUiModel: MovieDetailViewModel

    @Before
    fun setup() {
        getMovieDetailsUseCase = GetMovieDetailsUseCase(movieRepository)
        movieDetailUiModel = MovieDetailViewModel(getMovieDetailsUseCase)
    }

    @Test
    fun initViewModelStateShouldBeLoading() {
        assertEquals(UiState.Loading<MovieDetail>(),movieDetailUiModel.uiStateFlow.value)
    }

    @Test
    fun useCaseShouldReturnLoadingAndSuccess() = runTest {
        val useCaseFlowResult = mutableListOf<DataResult<MovieDetail>>()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            getMovieDetailsUseCase.invoke(movieId = 3).collect {
                useCaseFlowResult.add(it)
            }
        }
        assertEquals(useCaseFlowResult[0],DataResult.Loading<MovieDetail>())
        assertEquals(useCaseFlowResult[1],DataResult.Success(movieRepository.fakeMovieDetailInfo))
        collectJob.cancel()
    }

    @Test
    fun whenUseCaseSendsUiStateShouldBeUiLoading() = runTest {
    val uiStateFlowResult = mutableListOf<UiState<MovieDetailUiModel>>()
        val uiStateJob = launch(UnconfinedTestDispatcher()) { movieDetailUiModel.uiStateFlow.collect{
            uiStateFlowResult.add(it)
        } }
        movieDetailUiModel.reduceState(action = MovieDetailUiAction.LoadMovieDetail(3))
        assertEquals(UiState.Loading<MovieDetail>(),uiStateFlowResult[0])
        uiStateJob.cancel()
    }

    @Test
    fun uiStateSuccessShouldBeEqualToMovieDetailInfo() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { movieDetailUiModel.uiStateFlow.collect() }
        movieDetailUiModel.reduceState(action = MovieDetailUiAction.LoadMovieDetail(movieId = 3))
        assertEquals(movieRepository.fakeMovieDetailInfo, movieDetailUiModel.uiStateFlow.value.uiModel?.movieDetail)
        collectJob.cancel()
    }

}