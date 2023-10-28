package com.golnegari.composemovie.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.golnegari.common.base.navigation.NavRoutes
import com.golnegari.feature.popularmovies.PopularMoviesScreen


@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavRoutes.PopularMovieScreenNav.route,
    ) {
       composable(route = NavRoutes.PopularMovieScreenNav.route, arguments = NavRoutes.PopularMovieScreenNav.arguments) {
           PopularMoviesScreen(navHostController = navController)
       }

        composable(route = NavRoutes.MovieDetailNav.route , arguments = NavRoutes.MovieDetailNav.arguments) {

        }
    }
}
