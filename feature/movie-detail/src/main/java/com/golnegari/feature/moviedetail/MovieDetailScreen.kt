package com.golnegari.feature.moviedetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
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
    LaunchedEffect(key1 = Unit, block = {
        viewModel.sendAction(action = MovieDetailUiAction.LoadMovieDetail(movieId))
    })
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
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                data?.movieDetail?.let { movieDetailInfo ->
                    Header(movieInfo = movieDetailInfo, onBackPressed = {
                        navHostController.popBackStack()
                    })

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                    )

                    MovieGeneralInfoContent(
                        modifier = Modifier.fillMaxWidth(),
                        movieInfo = movieDetailInfo
                    )


                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                    )

                    movieDetailInfo.overview?.let {
                        MovieDescriptionContent(
                            description = it,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun Header(modifier: Modifier = Modifier, movieInfo: MovieDetail,onBackPressed: () -> Unit) {
    ConstraintLayout(modifier = modifier) {
        val (headerImage, movieImageRef, movieTitleRef, genreRef, topAppBarRef) = createRefs()
        AsyncImage(
            model = movieInfo.backdropPath,
            contentDescription = "posterPath",
            modifier = Modifier
                .fillMaxHeight()
                .height(250.dp)
                .constrainAs(headerImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, contentScale = ContentScale.Crop
        )
        AsyncImage(
            model = movieInfo.posterPath,
            contentDescription = null,
            modifier = Modifier
                .size(120.dp, height = 160.dp)
                .constrainAs(movieImageRef) {
                    start.linkTo(parent.start, margin = 24.dp)
                    top.linkTo(headerImage.bottom, margin = (-60).dp)
                }
                .clip(RoundedCornerShape(4.dp)), contentScale = ContentScale.Crop
        )
        movieInfo.originalTitle?.let {
            Text(
                text = it,
                textAlign = TextAlign.Start,
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                ),
                modifier = Modifier.constrainAs(movieTitleRef) {
                    start.linkTo(movieImageRef.end, margin = 12.dp)
                    top.linkTo(headerImage.bottom, margin = 12.dp)
                    end.linkTo(parent.end, margin = 12.dp)
                    width = Dimension.fillToConstraints
                })
        }
        if (movieInfo.genres.isNotEmpty()) {
            LazyHorizontalStaggeredGrid(
                rows = StaggeredGridCells.Fixed(2),
                Modifier
                    .height(80.dp)
                    .constrainAs(genreRef) {
                        start.linkTo(movieImageRef.end, margin = 12.dp)
                        end.linkTo(parent.end, margin = 12.dp)
                        top.linkTo(movieTitleRef.bottom, margin = 8.dp)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.wrapContent
                        width = Dimension.fillToConstraints
                    },
                horizontalItemSpacing = 6.dp,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(movieInfo.genres) { genre ->
                    AssistChip(onClick = {}, label = {
                        Text(text = genre.name, style = TextStyle(fontSize = 12.sp))

                    })
                }
            }
        }
        AppBar(modifier = Modifier.constrainAs(topAppBarRef){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        }, onBackPressed = onBackPressed)
    }
}

@Composable
private fun MovieGeneralInfoContent(modifier: Modifier = Modifier, movieInfo: MovieDetail) {

    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceAround) {
        MovieRatingContent(voteAverage = movieInfo.voteAverage, vote = movieInfo.voteCount)
        movieInfo.originalLanguage?.let {
            MovieLanguageContent(language = it)
        }
        movieInfo.releaseDate?.let {
            MovieReleaseContent(date = it)
        }
    }
}

@Composable
private fun MovieDescriptionContent(modifier: Modifier = Modifier, description: String) {
    Column(
        modifier = modifier.padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Overview",
            style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        )
        Text(text = description, style = TextStyle(fontSize = 14.sp))
    }
}

@Composable
private fun MovieRatingContent(modifier: Modifier = Modifier, voteAverage: Double, vote: Int) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = voteAverage.toString(),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            )

            Image(painter = painterResource(id = R.drawable.ic_rate), contentDescription = "rate")
        }
        Text(
            text = stringResource(id = R.string.average_vote_value, vote),
            textAlign = TextAlign.Center,
            style = TextStyle(color = Color.DarkGray, fontSize = 12.sp)
        )
    }
}

@Composable
private fun MovieLanguageContent(modifier: Modifier = Modifier, language: String) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_language),
                contentDescription = "langyage"
            )
            Text(
                text = language,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            )
        }
        Text(
            text = "language",
            textAlign = TextAlign.Center,
            style = TextStyle(color = Color.DarkGray, fontSize = 12.sp)
        )
    }
}

@Composable
private fun MovieReleaseContent(modifier: Modifier = Modifier, date: String) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_release_date),
                contentDescription = "release_date"
            )
            Text(
                text = date,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            )
        }
        Text(
            text = "Release date",
            textAlign = TextAlign.Center,
            style = TextStyle(fontSize = 12.sp)
        )
    }
}

@Composable
private fun AppBar(modifier: Modifier = Modifier,onBackPressed : () -> Unit) {
    ConstraintLayout(
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth()
    ) {
        val (backRef, shareRef, favoriteRef) = createRefs()
        IconButton(onClick = { onBackPressed.invoke()}, modifier = Modifier.constrainAs(backRef) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
        }) {
            Image(
                colorFilter = ColorFilter.tint(color = Color.White),
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "back"
            )
        }

        IconButton(onClick = { }, modifier = Modifier.constrainAs(favoriteRef) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
        }) {
            Image(
                colorFilter = ColorFilter.tint(color = Color.White),
                painter = painterResource(id = R.drawable.ic_favorite),
                contentDescription = "favorite",
            )
        }

        IconButton(onClick = { }, modifier = Modifier.constrainAs(shareRef) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            end.linkTo(favoriteRef.start)
        }) {
            Image(
                colorFilter = ColorFilter.tint(color = Color.White),
                painter = painterResource(id = R.drawable.ic_share),
                contentDescription = "favorite",
            )
        }
    }
}