package com.example.mobilliumcase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mobilliumcase.data.HomeFragmentRepository
import com.example.mobilliumcase.model.ApiResponse
import com.example.mobilliumcase.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val homeFragmentRepository: HomeFragmentRepository): ViewModel() {
    suspend fun fetchNowPlaying(): Flow<ApiResponse> {
        return homeFragmentRepository.getNowPlaying()
    }

    fun fetchUpcoming(): Flow<PagingData<Result>> {
        return homeFragmentRepository.getUpcoming()
            .cachedIn(viewModelScope)
    }
}