package com.example.mobilliumcase.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilliumcase.model.MovieDetail
import com.example.mobilliumcase.network.ApiRequest
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class DetailFragmentViewModel @Inject constructor(): ViewModel() {
    @Inject
    lateinit var apiRequest: ApiRequest

    private val _detailResponse: MutableLiveData<MovieDetail> = MutableLiveData()
    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData()

    init {
        _isLoading.value = false

        // Create dummy data
        _detailResponse.value = MovieDetail(
            backdrop_path = "",
            id = 0,
            overview = "",
            release_date = "",
            title = "",
            imdb_id = "",
            vote_average = 0.0
        )
    }

    fun fetchMovieDetail(movie_id: Int) {
        _isLoading.value = true

        viewModelScope.launch {
            apiRequest.getDetail(movie_id).enqueue(object: Callback<MovieDetail> {
                override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                    _detailResponse.postValue(response.body())

                    _isLoading.value = false
                }

                override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                    // TODO: Handle errors...

                    _isLoading.value = false
                }
            })
        }
    }

    fun getDetailObservable(): MutableLiveData<MovieDetail> {
        return _detailResponse
    }

    fun isLoading(): MutableLiveData<Boolean> {
        return _isLoading
    }
}