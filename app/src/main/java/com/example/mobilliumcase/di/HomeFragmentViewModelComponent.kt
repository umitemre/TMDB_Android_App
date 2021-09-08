package com.example.mobilliumcase.di

import com.example.mobilliumcase.network.ApiRequest
import com.example.mobilliumcase.viewmodel.HomeFragmentViewModel
import dagger.Component

@Component(modules = [ApiRequestModule::class])
interface HomeFragmentViewModelComponent {
    var apiRequest: ApiRequest
    fun inject(viewModel: HomeFragmentViewModel)
}