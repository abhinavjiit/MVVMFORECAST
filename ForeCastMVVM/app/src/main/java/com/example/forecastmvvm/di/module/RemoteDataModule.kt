package com.example.forecastmvvm.di.module

import com.example.forecastmvvm.data.network.repo.FetchCurrentWeatherFromApi
import com.example.forecastmvvm.data.network.repo.FetchCurrentWeatherFromApiImpl
import com.example.forecastmvvm.data.network.weatherApiInterface.WeatherApiInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteDataModule {
    @Singleton
    @Provides
    fun provideMovieLocalDataSource(): FetchCurrentWeatherFromApi {
        return FetchCurrentWeatherFromApiImpl()
    }
}