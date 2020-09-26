package com.example.forecastmvvm.di.module

import com.example.forecastmvvm.data.db.WeatherDataBaseDaoInterface
import com.example.forecastmvvm.data.network.repo.FetchCurrentWeatherFromLocal
import com.example.forecastmvvm.data.network.repo.FetchCurrentWeatherFromLocalImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataModule(private val weatherDataBaseDaoInterface: WeatherDataBaseDaoInterface) {
    @Singleton
    @Provides
    fun provideMovieLocalDataSource(): FetchCurrentWeatherFromLocal {
        return FetchCurrentWeatherFromLocalImpl(weatherDataBaseDaoInterface)
    }
}