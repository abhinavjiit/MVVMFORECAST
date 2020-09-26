package com.example.forecastmvvm.data.network.repo

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.forecastmvvm.BaseApplication
import com.example.forecastmvvm.Resource
import com.example.forecastmvvm.data.db.DataBaseClass
import com.example.forecastmvvm.data.network.weatherApiInterface.WeatherApiInterface
import com.example.forecastmvvm.data.response.CurrentWeatherEntry
import com.example.forecastmvvm.sharedPref.SharedPrefUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

object CurrentWeatherRepo {
    private var job: Job? = null
    private var dataBase = DataBaseClass.invoke(BaseApplication.getInstance())
    suspend fun currentWeatherData(): MutableLiveData<Resource<CurrentWeatherEntry>> {
        val currentWeatherResponse = MutableLiveData<Resource<CurrentWeatherEntry>>()

        if (!isOnline() && !isLocationEnabled()) {
            try {
                throw  Exception()
            } catch (e: Exception) {
                Log.d("ldku", e.toString())
                currentWeatherResponse.value = Resource.error(e.message.toString(), null)
            }
            job = CoroutineScope(Dispatchers.Main).launch {
                val data = async(IO) { dataBase.weatherDao().getCurrentWeather() }
                val res = data.await()
                currentWeatherResponse.value = Resource.success(res)
            }
        } else {
            job = CoroutineScope(Dispatchers.Main).launch {
                SharedPrefUtil.getLocality(BaseApplication.getInstance())?.let {
                    val res = async(IO) {
                        WeatherApiInterface.invoke("http://api.weatherstack.com")
                            .getCurrentWeatherAsync(it)
                    }
                    val response = res.await()
                    launch(IO) { dataBase.weatherDao().upsert(response.currentWeatherEntry) }
                    currentWeatherResponse.value =
                        Resource.success(response.currentWeatherEntry)
                }
            }

        }

        job?.join()
        return currentWeatherResponse
    }


    private fun isOnline(): Boolean {
        val connectivityManager =
            BaseApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetwork
        val actNw =
            connectivityManager.getNetworkCapabilities(networkInfo) ?: return false
        return actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) && actNw.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_VALIDATED
        )
    }

    private fun isLocationEnabled(): Boolean {
        val lm = BaseApplication.getInstance()
            .getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


}