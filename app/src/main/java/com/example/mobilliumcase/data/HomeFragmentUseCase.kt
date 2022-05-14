package com.example.mobilliumcase.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mobilliumcase.model.ApiResponse
import com.example.mobilliumcase.model.Result
import com.example.mobilliumcase.network.ApiRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeFragmentUseCase @Inject constructor(private val apiRequest: ApiRequest) {
    suspend fun getNowPlaying(): Flow<ApiResponse> {
        return flow {
            emit(apiRequest.getNowPlaying())
        }
    }

    fun getUpcoming(): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(pageSize = 20, initialLoadSize = 40),
            pagingSourceFactory = { UpcomingPagingSource(apiRequest) }
        ).flow
    }
}