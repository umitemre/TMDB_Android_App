package com.example.mobilliumcase.di

import com.example.mobilliumcase.network.ApiRequest
import com.example.mobilliumcase.viewmodel.DetailFragmentViewModel
import com.example.mobilliumcase.viewmodel.HomeFragmentViewModel
import dagger.Component

@Component(modules = [ApiRequestModule::class])
interface DetailFragmentViewModelComponent {
    var apiRequest: ApiRequest
    fun inject(viewModel: DetailFragmentViewModel)
}