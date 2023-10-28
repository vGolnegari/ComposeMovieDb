package com.golnegari.core.domain.usecase

import com.golnegari.core.domain.base.DataResult
import com.golnegari.core.domain.base.DomainModelList
import com.golnegari.core.domain.model.Movie
import com.golnegari.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    operator fun invoke() : Flow<DataResult<DomainModelList<Movie>>> = flow {
        emit(DataResult.Loading())
        emit(movieRepository.getPopularMovie())
    }
}