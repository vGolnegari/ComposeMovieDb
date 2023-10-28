package com.golnegari.core.domain.usecase

import androidx.paging.PagingData
import com.golnegari.core.domain.model.Movie
import com.golnegari.core.domain.repository.MovieRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
@ViewModelScoped
class SyncPopularMovieUseCase @Inject constructor(private val movieRepository: MovieRepository) {

     operator fun invoke() : Flow<PagingData<Movie>> {
        return movieRepository.syncPopularMovies()
    }

}