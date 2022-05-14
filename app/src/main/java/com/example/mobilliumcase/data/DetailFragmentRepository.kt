package com.example.mobilliumcase.data

import com.example.mobilliumcase.model.MovieDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailFragmentRepository @Inject constructor(var useCase: DetailFragmentUseCase) {
    suspend fun getDetail(movie_id: Int): Flow<MovieDetail> {
        return useCase.getDetail(movie_id)
    }
}