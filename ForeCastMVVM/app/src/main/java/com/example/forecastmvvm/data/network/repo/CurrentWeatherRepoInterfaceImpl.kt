package com.example.forecastmvvm.data.network.repo

import com.example.forecastmvvm.Resource
import com.example.forecastmvvm.data.response.CurrentWeatherEntry
import com.example.forecastmvvm.data.response.Hourly

class CurrentWeatherRepoInterfaceImpl(
    val fetchCurrentWeatherFromApi: FetchCurrentWeatherFromApi,
    val fetchCurrentWeatherFromLocal: FetchCurrentWeatherFromLocal
) : CurrentWeatherRepoInterface {
    override suspend fun getCurrentWeather(): Resource<CurrentWeatherEntry> {
        return fetchCurrentWeatherFromApi.getCurrentWeatherApi()
    }

    override suspend fun updateCurrentWeather(currentWeatherEntry: CurrentWeatherEntry) {
        fetchCurrentWeatherFromLocal.updateCurrentWeatherLocal(currentWeatherEntry)
    }

    override suspend fun getFromLocal(): CurrentWeatherEntry {
        return fetchCurrentWeatherFromLocal.getCurrentWeatherLocal()
    }

    override suspend fun getHourly(): Resource<List<Hourly>> {
        return fetchCurrentWeatherFromApi.getHourlyWeatherApi()
    }
}