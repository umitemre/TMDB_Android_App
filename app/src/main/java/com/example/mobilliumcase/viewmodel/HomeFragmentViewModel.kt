package com.example.mobilliumcase.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilliumcase.model.ApiResponse
import com.example.mobilliumcase.network.ApiRequest
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

private const val TAG = "HomeFragmentViewModel"
class HomeFragmentViewModel @Inject constructor(): ViewModel() {
    var nowPlayingPage: Int = 1
    var isLoading: Boolean = false

    @Inject
    lateinit var apiRequest: ApiRequest

    private val _nowPlayingResponse: MutableLiveData<ApiResponse> = MutableLiveData()
    private val _upComingResponse: MutableLiveData<ApiResponse> = MutableLiveData()

    var nowPlayingAck = false
    var upComingAck = false

    fun fetchNowPlaying() {
        viewModelScope.launch {
            apiRequest.getNowPlaying().enqueue(object: Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    _nowPlayingResponse.postValue(response.body())
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    // TODO: Handle errors...
                }
            })
        }
    }

    fun fetchUpcoming() {
        viewModelScope.launch {
            apiRequest.getUpcoming(nowPlayingPage).enqueue(object: Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    _upComingResponse.postValue(response.body())
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    // TODO: Handle errors...
                }
            })
        }
    }

    fun getNowPlayingObservable(): MutableLiveData<ApiResponse> {
        return _nowPlayingResponse
    }

    fun getUpcomingObservable(): MutableLiveData<ApiResponse> {
        return _upComingResponse
    }

    fun getNextPage() {
        nowPlayingPage += 1

        upComingAck = false
        fetchUpcoming()
    }
}