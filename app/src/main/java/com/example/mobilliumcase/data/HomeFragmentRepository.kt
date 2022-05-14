package com.example.mobilliumcase.data

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mobilliumcase.model.ApiResponse
import com.example.mobilliumcase.model.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeFragmentRepository @Inject constructor(var useCase: HomeFragmentUseCase) {
    suspend fun getNowPlaying(): Flow<ApiResponse> {
        return useCase.getNowPlaying()
    }

    fun getUpcoming(): Flow<PagingData<Result>> {
        return useCase.getUpcoming()
    }
}