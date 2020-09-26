package com.example.forecastmvvm.data.network.repo

import com.example.forecastmvvm.data.db.WeatherDataBaseDaoInterface
import com.example.forecastmvvm.data.response.CurrentWeatherEntry
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class FetchCurrentWeatherFromLocalImpl(private val weatherDao: WeatherDataBaseDaoInterface) :
    FetchCurrentWeatherFromLocal {
    lateinit var res1: CurrentWeatherEntry
    override suspend fun getCurrentWeatherLocal(): CurrentWeatherEntry {
        val job = CoroutineScope(Dispatchers.Main).launch {
            val res = async(IO) { weatherDao.getCurrentWeather()!! }
            res1 = res.await()
        }
        job.join()
        return res1
    }

    override suspend fun updateCurrentWeatherLocal(currentWeather: CurrentWeatherEntry) {
        GlobalScope.launch(Dispatchers.IO) {
            weatherDao.upsert(currentWeather)
        }
    }
}