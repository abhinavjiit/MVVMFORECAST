package com.example.forecastmvvm.data.network.repo

import com.example.forecastmvvm.BaseApplication
import com.example.forecastmvvm.Resource
import com.example.forecastmvvm.data.network.weatherApiInterface.API_KEY2
import com.example.forecastmvvm.data.network.weatherApiInterface.WeatherApiInterface
import com.example.forecastmvvm.data.response.CurrentWeatherEntry
import com.example.forecastmvvm.data.response.Hourly
import com.example.forecastmvvm.sharedPref.SharedPrefUtil

class FetchCurrentWeatherFromApiImpl() :
    FetchCurrentWeatherFromApi {
    override suspend fun getCurrentWeatherApi(): Resource<CurrentWeatherEntry> {
        try {
            val res = WeatherApiInterface.invoke("http://api.weatherstack.com/")
                .getCurrentWeatherAsync(SharedPrefUtil.getLocality(BaseApplication.getInstance())!!)
            return Resource.success(res.currentWeatherEntry)
        } catch (e: Exception) {
            return Resource.error(e.message.toString(), null)
        }

    }

    override suspend fun getHourlyWeatherApi(): Resource<List<Hourly>> {
        try {
            val res = WeatherApiInterface.invoke("https://api.openweathermap.org/").getForecastData(
                SharedPrefUtil.getLatitude(BaseApplication.getInstance()).toDouble(),
                SharedPrefUtil.getLongitude(BaseApplication.getInstance()).toDouble(),
                API_KEY2
            )
            return Resource.success(res.hourly)
        } catch (e: java.lang.Exception) {
            return Resource.error(e.message.toString(), null)
        }
    }
}