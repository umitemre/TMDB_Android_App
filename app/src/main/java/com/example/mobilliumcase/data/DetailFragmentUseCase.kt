package com.example.mobilliumcase.data

import com.example.mobilliumcase.model.MovieDetail
import com.example.mobilliumcase.network.ApiRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailFragmentUseCase @Inject constructor(val apiRequest: ApiRequest) {
    suspend fun getDetail(movie_id: Int): Flow<MovieDetail> {
        return flow {
            emit(apiRequest.getDetail(movie_id))
        }
    }
}