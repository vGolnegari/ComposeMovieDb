package com.golnegari.feature.popularmovies

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.golnegari.common.base.ui.CommonScreen
import com.golnegari.core.domain.model.Movie

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PopularMoviesScreen(
    viewModel: PopularMoviesViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.sendAction(action = PopularMoviesAction.GetPopularMovies)
    })
    CommonScreen(
        navController = navHostController,
        uiStateState = viewModel.uiStateFlow.collectAsState(),
        singleEventFlow = viewModel.singleEventFlow,
        sendAction = viewModel::sendAction
    ) {data, snackBarHostState ->
        Scaffold(snackbarHost = { SnackbarHost(hostState = snackBarHostState) }) { paddingValues ->
            LazyVerticalStaggeredGrid(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp),
                columns = StaggeredGridCells.Fixed(count = 3),
                verticalItemSpacing = 4.dp,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                ){
                items(data?.movies ?: emptyList()) { item ->
                    MovieItemContent(info =item , onMovieCLicked = {id ->
                        viewModel.sendAction(action = PopularMoviesAction.OnGotoMovieDetail(movieId = id))
                    })
                }
            }
        }
    }
}

@Composable
private fun MovieItemContent(modifier: Modifier = Modifier,info : Movie,onMovieCLicked : (movieId : Int) -> Unit) {
    Column(modifier = modifier
        .height(180.dp)
        .background(color = Color.DarkGray)
        .clickable {
            onMovieCLicked.invoke(info.id)
        }, verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(model = info.posterPath, contentDescription = "poster", modifier = Modifier.height(140.dp), contentScale = ContentScale.Crop)
        Text(modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),text = info.title, fontSize = 14.sp,color = Color.White, overflow = TextOverflow.Ellipsis, maxLines = 2, textAlign = TextAlign.Center)
    }
}