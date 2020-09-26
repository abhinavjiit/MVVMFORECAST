package com.example.forecastmvvm.di.module

import com.example.forecastmvvm.data.network.repo.CurrentWeatherRepoInterface
import com.example.forecastmvvm.data.network.repo.CurrentWeatherRepoInterfaceImpl
import com.example.forecastmvvm.data.network.repo.FetchCurrentWeatherFromApi
import com.example.forecastmvvm.data.network.repo.FetchCurrentWeatherFromLocal
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Provides
    @Singleton
    fun provideCurrentWeatherRepo(
        fetchCurrentWeatherFromApi: FetchCurrentWeatherFromApi,
        fetchCurrentWeatherFromLocal: FetchCurrentWeatherFromLocal
    ): CurrentWeatherRepoInterface {
        return CurrentWeatherRepoInterfaceImpl(
            fetchCurrentWeatherFromApi,
            fetchCurrentWeatherFromLocal
        )

    }
}