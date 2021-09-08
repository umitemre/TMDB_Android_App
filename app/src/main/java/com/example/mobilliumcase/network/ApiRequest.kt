package com.example.mobilliumcase.network

import com.example.mobilliumcase.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiRequest {
    @GET("movie/upcoming?api_key=995f25cba82ccec09536a919b364957d")
    fun getUpcoming(): Call<ApiResponse>

    @GET("movie/now_playing?api_key=995f25cba82ccec09536a919b364957d")
    fun getNowPlaying(): Call<ApiResponse>
}