package com.example.forecastmvvm.data.network.repo

import com.example.forecastmvvm.Resource
import com.example.forecastmvvm.data.response.CurrentWeatherEntry
import com.example.forecastmvvm.data.response.Hourly

interface FetchCurrentWeatherFromApi {

    suspend fun getCurrentWeatherApi(): Resource<CurrentWeatherEntry>
    suspend fun getHourlyWeatherApi(): Resource<List<Hourly>>
}