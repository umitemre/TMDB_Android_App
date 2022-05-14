package com.example.mobilliumcase.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mobilliumcase.data.DetailFragmentRepository
import com.example.mobilliumcase.model.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DetailFragmentViewModel @Inject constructor(var repository: DetailFragmentRepository): ViewModel() {
    suspend fun fetchMovieDetail(movie_id: Int): Flow<MovieDetail> {
        return repository.getDetail(movie_id)
    }
}