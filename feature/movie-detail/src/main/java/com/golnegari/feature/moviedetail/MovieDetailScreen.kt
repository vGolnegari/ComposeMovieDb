package com.golnegari.feature.moviedetail

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.golnegari.common.base.ui.CommonScreen
import com.golnegari.core.domain.model.MovieDetail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movieId: Int,
    viewModel: MovieDetailViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    CommonScreen(
        navController = navHostController,
        uiStateState = viewModel.uiStateFlow.collectAsState(),
        singleEventFlow = viewModel.singleEventFlow,
        sendAction = viewModel::sendAction
    ) { data, snackBarHostState ->
        Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                data?.movieDetail.let { movieDetailInfo ->

                }
            }
        }
    }
}

@Composable
private fun Header(modifier: Modifier = Modifier, movieInfo: MovieDetail) {
    ConstraintLayout(modifier = modifier) {
        val (headerImage, movieImageRef, movieTitleRef) = createRefs()
        AsyncImage(model = movieInfo.poster_path,
            contentDescription = "posterPath",
            modifier = Modifier
                .fillMaxHeight()
                .height(250.dp)
                .constrainAs(headerImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
        AsyncImage(
            model = movieInfo.backdrop_path,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp, height = 160.dp)
                .constrainAs(movieImageRef) {
                    start.linkTo(parent.start, margin = 24.dp)
                    top.linkTo(headerImage.bottom, margin = (-40).dp)
                }
        )
        movieInfo.original_title?.let {
            Text(text = it, textAlign = TextAlign.Start, color = Color.Black)

        }
    }

}