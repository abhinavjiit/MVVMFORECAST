package com.example.forecastmvvm.data.network.repo

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.forecastmvvm.BaseApplication
import com.example.forecastmvvm.Resource
import com.example.forecastmvvm.data.network.weatherApiInterface.API_KEY2
import com.example.forecastmvvm.data.network.weatherApiInterface.WeatherApiInterface
import com.example.forecastmvvm.data.response.Daily
import com.example.forecastmvvm.sharedPref.SharedPrefUtil
import kotlinx.coroutines.*


object FutureListWeatherRepo {

    private var job: Job? = null

    suspend fun futureWeatherDataList(): MutableLiveData<Resource<List<Daily>>> {
        val futureWeatherResponseList = MutableLiveData<Resource<List<Daily>>>()

        if (!isOnline()) {
            try {
                throw  Exception()
            } catch (e: Exception) {
                Log.d("ldku", e.toString())
                futureWeatherResponseList.value = Resource.error(e.message.toString(), null)
            }
            job = CoroutineScope(Dispatchers.Main).launch {

                futureWeatherResponseList.value = Resource.error("no network", null)

            }

        } else {
            job = CoroutineScope(Dispatchers.Main).launch {


                val res = async(Dispatchers.IO) {
                    WeatherApiInterface.invoke("https://api.openweathermap.org")
                        .getForecastData(
                            SharedPrefUtil.getLatitude(BaseApplication.getInstance()).toDouble(),
                            SharedPrefUtil.getLongitude(BaseApplication.getInstance()).toDouble(),
                            API_KEY2

                        )
                }
                val response = res.await()
                futureWeatherResponseList.value = Resource.success(response.daily)
            }


        }

        job?.join()
        return futureWeatherResponseList
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

}