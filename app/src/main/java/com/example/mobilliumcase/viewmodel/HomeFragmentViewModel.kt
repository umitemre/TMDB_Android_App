package com.example.mobilliumcase.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobilliumcase.model.ApiResponse
import com.example.mobilliumcase.network.ApiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import javax.inject.Inject

private const val TAG = "HomeFragmentViewModel"
class HomeFragmentViewModel @Inject constructor(): ViewModel() {
    @Inject
    lateinit var apiRequest: ApiRequest

    private val _nowPlayingResponse: MutableLiveData<ApiResponse> = MutableLiveData()
    private val _upComingResponse: MutableLiveData<ApiResponse> = MutableLiveData()

    fun fetchNowPlaying() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = apiRequest.getNowPlaying().awaitResponse()
            if (response.isSuccessful) {
                _nowPlayingResponse.postValue(response.body())
            } else {
                // TODO: Handle errors...
            }
        }
    }

    fun fetchUpcoming() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = apiRequest.getUpcoming().awaitResponse()
            if (response.isSuccessful) {
                _upComingResponse.postValue(response.body())
            } else {
                // TODO: Handle errors...
            }
        }
    }

    fun getNowPlayingObservable(): MutableLiveData<ApiResponse> {
        return _nowPlayingResponse
    }

    fun getUpcomingObservable(): MutableLiveData<ApiResponse> {
        return _upComingResponse
    }
}