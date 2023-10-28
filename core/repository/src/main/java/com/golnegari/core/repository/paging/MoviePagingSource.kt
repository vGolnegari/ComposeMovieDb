package com.golnegari.core.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.golnegari.core.domain.model.Movie
import com.golnegari.core.network.datasource.RemoteMovieDataSource
import com.golnegari.core.repository.toDomainMovie

class MoviePagingSource constructor(private val remoteMovieDataSource: RemoteMovieDataSource) :
    PagingSource<Int,  Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val movieResponse = remoteMovieDataSource.fetchPopularMovieList(page)
                val popularMovies = movieResponse.result?.map { it.toDomainMovie() } ?: emptyList()
                LoadResult.Page(
                    data = popularMovies,
                    prevKey = null,
                    nextKey = if (popularMovies.isEmpty()) null else page.plus(1)
                )
        } catch (exp : Exception) {
            LoadResult.Error(exp)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }
}