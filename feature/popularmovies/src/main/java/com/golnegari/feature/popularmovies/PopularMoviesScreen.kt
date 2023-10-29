package com.golnegari.feature.popularmovies

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.golnegari.common.base.ui.CommonScreen
import com.golnegari.core.domain.model.Movie
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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
    ) { data, snackBarHostState ->
        Scaffold(snackbarHost = { SnackbarHost(hostState = snackBarHostState) }, topBar = {
            TopAppBar(title = { Text(text = "Popular", color = Color.White) }, actions = {
                IconButton(onClick = { }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_arrangement),
                        contentDescription = "arrangement",
                        colorFilter = ColorFilter.tint(
                            Color.White
                        )
                    )
                }
            }, colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.DarkGray))
        }) { paddingValues ->
            data?.let {
                val coroutineScope = rememberCoroutineScope()
                val movies = data.pageableMovies.collectAsLazyPagingItems()
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(8.dp),
                    columns = GridCells.Fixed(count = 3),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(movies.itemCount) { index ->
                        movies[index]?.let { movie ->
                            MovieItemContent(info = movie, onMovieCLicked = { id ->
                                viewModel.sendAction(
                                    action = PopularMoviesAction.OnGotoMovieDetail(
                                        movieId = id
                                    )
                                )
                            })
                        }
                    }
                        when(movies.loadState.refresh) {
                            is LoadState.Loading -> {
                                items(20) {
                                    LoadingScreen()
                                }
                            }

                            is LoadState.Error -> {
                                coroutineScope.launch {
                                    snackBarHostState.showSnackbar("Ops! Something was Wrong",actionLabel = null)
                                }
                            }

                            else -> {
                            }
                        }

                        when(movies.loadState.append) {
                            is LoadState.Loading -> {
                                items(8) {
                                    LoadingScreen(modifier = Modifier.height(200.dp))
                                }
                            }

                            is LoadState.Error -> {
                                coroutineScope.launch {
                                    snackBarHostState.showSnackbar("Ops! Something was Wrong",actionLabel = null)
                                }
                            }

                            else -> {}
                        }

                }
            }
        }
    }
}

@Composable
private fun MovieItemContent(
    modifier: Modifier = Modifier,
    info: Movie,
    onMovieCLicked: (movieId: Int) -> Unit
) {
    Column(
        modifier = modifier
            .height(200.dp)
            .background(color = Color.DarkGray)
            .clickable {
                onMovieCLicked.invoke(info.id)
            },
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = info.posterPath,
            contentDescription = "poster",
            modifier = Modifier.height(140.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 4.dp),
            text = info.title,
            style = TextStyle(fontSize = 12.sp, color = Color.White),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun LoadingScreen(modifier: Modifier = Modifier){
    Column(modifier = modifier.height(200.dp).background(color = Color.LightGray) , verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator()
    }
}