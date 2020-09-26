package com.example.forecastmvvm.data.network.repo

import com.example.forecastmvvm.data.response.CurrentWeatherEntry

interface FetchCurrentWeatherFromLocal {
    suspend fun getCurrentWeatherLocal():CurrentWeatherEntry
    suspend fun updateCurrentWeatherLocal(currentWeather: CurrentWeatherEntry)
}