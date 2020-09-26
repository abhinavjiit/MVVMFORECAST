package com.example.forecastmvvm.di

import com.example.forecastmvvm.di.module.CurrentWeatherModule
import com.example.forecastmvvm.ui.fragment.current.CurrentWeatherFragment
import dagger.Subcomponent

@CurrentWeatherScope
@Subcomponent(modules = [CurrentWeatherModule::class])
interface CurrentWeatherSubComponent {
    fun inject(currentWeatherFragment: CurrentWeatherFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): CurrentWeatherSubComponent
    }
}