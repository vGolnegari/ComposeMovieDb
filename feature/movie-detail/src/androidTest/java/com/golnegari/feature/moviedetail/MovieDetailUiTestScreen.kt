package com.golnegari.feature.moviedetail

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.testing.TestNavHostController
import com.golnegari.core.domain.usecase.GetMovieDetailsUseCase
import com.golnegari.core.testing.repository.FakeSucceedMovieRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieDetailUiTestScreen {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase
    private val movieRepository = FakeSucceedMovieRepository()
    private lateinit var movieDetailUiModel: MovieDetailViewModel

    @Before
    fun setup() {
        getMovieDetailsUseCase = GetMovieDetailsUseCase(movieRepository)
        movieDetailUiModel = MovieDetailViewModel(getMovieDetailsUseCase)
        composeTestRule.setContent {
            val navController = TestNavHostController(LocalContext.current)
            MovieDetailScreen(
                movieId = 3,
                navHostController = navController,
                viewModel = movieDetailUiModel
            )
        }
    }

    @Test
    fun whenMovieDetailLoadedThenMovieDetailShouldHaveSameName() {
        composeTestRule.onNodeWithText("Harry Potter").assertIsDisplayed()
    }

    @Test
    fun whenMovieDetailLoadedThenOverviewShouldHaveSameName() {
        composeTestRule.onNodeWithText("Harry Potter and Half blood Princess").assertIsDisplayed()
    }

    @Test
    fun whenMovieDetailLoadedThenVoteCountShouldHaveSameName() {
        composeTestRule.onNodeWithText(composeTestRule.activity.resources.getString(R.string.average_vote_value,1330)).assertIsDisplayed()
    }

    @Test
    fun whenMovieDetailLoadedThenVoteAverageShouldHaveSameName() {
        composeTestRule.onNodeWithText("8.9").assertIsDisplayed()
    }

    @Test
    fun whenMovieDetailLoadedThenOriginalShouldHaveSameName() {
        composeTestRule.onNodeWithText("en").assertIsDisplayed()
    }
}