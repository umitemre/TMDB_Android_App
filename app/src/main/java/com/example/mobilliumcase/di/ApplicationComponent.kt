package com.example.mobilliumcase.di

import com.example.mobilliumcase.app.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    modules = [
        ActivityBuildersModule::class,
        AndroidSupportInjectionModule::class,
        FragmentBuildersModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<App> {
    override fun inject(instance: App?)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): ApplicationComponent
    }
}