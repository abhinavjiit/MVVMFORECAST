package com.example.forecastmvvm.di.module

import com.example.forecastmvvm.data.network.repo.CurrentWeatherRepoInterface
import com.example.forecastmvvm.di.CurrentWeatherScope
import com.example.forecastmvvm.ui.fragment.current.CurrentViewModelFactory
import dagger.Module
import dagger.Provides


@Module
class CurrentWeatherModule {
    @CurrentWeatherScope
    @Provides
    fun provideMovieViewModelFactory(
        currentWeatherRepoInterface: CurrentWeatherRepoInterface
    ): CurrentViewModelFactory {
        return CurrentViewModelFactory(
            currentWeatherRepoInterface
        )
    }

}