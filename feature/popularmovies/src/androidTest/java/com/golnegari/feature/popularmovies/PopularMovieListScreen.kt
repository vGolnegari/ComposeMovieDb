package com.golnegari.feature.popularmovies

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.testing.TestNavHostController
import com.golnegari.core.domain.model.Movie
import com.golnegari.core.domain.usecase.SyncPopularMovieUseCase
import com.golnegari.core.testing.data.fakePopularMovieList
import com.golnegari.core.testing.repository.FakeSucceedMovieRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PopularMovieListScreen {

    @get:Rule
    var composeRule = createComposeRule()

    @Before
    fun setup(){
        val fakeMovieRepository = FakeSucceedMovieRepository()
        val syncPopularMovieUseCase = SyncPopularMovieUseCase(fakeMovieRepository)
        val popularMovieViewModel = PopularMoviesViewModel(syncPopularMovieUseCase)
        composeRule.setContent {
            val navController = TestNavHostController(LocalContext.current)
            PopularMoviesScreen(navHostController = navController, viewModel = popularMovieViewModel)
        }
    }

    @Test
    fun whenPopularMoviesShownEachMovieCellShouldShownOwnMovieTitle(){
        fakePopularMovieList.forEach { movie: Movie ->
            composeRule.onNodeWithText(movie.title).assertIsDisplayed()
        }
    }


}