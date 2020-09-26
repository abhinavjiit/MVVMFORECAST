package com.example.forecastmvvm.ui.fragment.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.forecastmvvm.Resource
import com.example.forecastmvvm.data.network.repo.CurrentWeatherRepoInterface
import com.example.forecastmvvm.data.response.Hourly

class CurrentWeatherViewModel(val currentWeatherRepoInterface: CurrentWeatherRepoInterface) :
    ViewModel() {

    fun getWeather() = liveData {
        val res = currentWeatherRepoInterface.getCurrentWeather()
        res.data?.let {
            currentWeatherRepoInterface.updateCurrentWeather(it)
        }
        emit(res)
    }

    fun getWeatherFromLocal() = liveData {
        val localRes = currentWeatherRepoInterface.getFromLocal()
        emit(localRes)
    }


    fun getHourlyWeather() = liveData<Resource<List<Hourly>>> {
        val hourlyWeather = currentWeatherRepoInterface.getHourly()
        emit(hourlyWeather)
    }


}