package com.example.forecastmvvm.data.network.repo

import com.example.forecastmvvm.Resource
import com.example.forecastmvvm.data.response.CurrentWeatherEntry
import com.example.forecastmvvm.data.response.Hourly

interface CurrentWeatherRepoInterface {

    suspend fun getCurrentWeather(): Resource<CurrentWeatherEntry>
    suspend fun updateCurrentWeather(currentWeatherEntry: CurrentWeatherEntry)
    suspend fun getFromLocal(): CurrentWeatherEntry
    suspend fun getHourly(): Resource<List<Hourly>>

}