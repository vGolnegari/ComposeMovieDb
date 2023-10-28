package com.golnegari.common.base.navigation


import androidx.navigation.*

private const val POPULAR_ROUTE = "popularMovieListScreen"
private const val MOVIE_DETAIL_ROUTE = "movieDetailScreen/%s"
private const val MOVIE_ID_ARGUMENT = "movieId"

sealed class NavRoutes(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()) {

    object PopularMovieScreenNav : NavRoutes(route = POPULAR_ROUTE)

    object MovieDetailNav : NavRoutes(route = String.format(MOVIE_DETAIL_ROUTE,"{$MOVIE_ID_ARGUMENT}"), arguments = listOf(
        navArgument(MOVIE_ID_ARGUMENT) {
            type = NavType.IntType
        }
    )) {
        fun navigateToMovieDetail(movieId : Int) = String.format(MOVIE_DETAIL_ROUTE,movieId.toString())

        fun getMovieId(navBackStackEntry: NavBackStackEntry) : Int = navBackStackEntry.arguments?.getInt(
            MOVIE_ID_ARGUMENT) ?: 0
    }

}
