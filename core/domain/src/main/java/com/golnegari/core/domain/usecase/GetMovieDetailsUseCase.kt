package com.golnegari.core.domain.usecase

import com.golnegari.core.domain.base.DataResult
import com.golnegari.core.domain.model.Movie
import com.golnegari.core.domain.model.MovieDetail
import com.golnegari.core.domain.repository.MovieRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
@ViewModelScoped
class GetMovieDetailsUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    operator fun invoke(movieId: Int) : Flow<DataResult<MovieDetail>> = flow {
        emit(DataResult.Loading())
        emit(movieRepository.getMovieDetail(movieId))
    }
}