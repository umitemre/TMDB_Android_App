package com.example.mobilliumcase.di

import com.example.mobilliumcase.network.ApiRequest
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiRequestModule {
    @Provides
    fun provideApiRequest(retrofit: Retrofit): ApiRequest {
        return retrofit.create(ApiRequest::class.java)
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}