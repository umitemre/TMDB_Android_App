package com.example.mobilliumcase.di

import com.example.mobilliumcase.fragment.DetailFragment
import com.example.mobilliumcase.fragment.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): DetailFragment
}