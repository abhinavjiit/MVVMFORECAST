package com.example.forecastmvvm.ui.fragment.future

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forecastmvvm.Resource
import com.example.forecastmvvm.data.network.repo.FutureListWeatherRepo
import com.example.forecastmvvm.data.response.Daily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FutureListWeatherViewModel : ViewModel() {
    private var futureWeatherResponse: MutableLiveData<Resource<List<Daily>>>? = null
    private var futureWeather1 = MutableLiveData<Resource<List<Daily>>>()

    init {
        futureWeather1.value = Resource.loading(null)
        CoroutineScope(Dispatchers.Main).launch {
            futureWeatherResponse = withContext(Dispatchers.Main) {
                FutureListWeatherRepo.futureWeatherDataList()
            }
            futureWeather1.value = futureWeatherResponse?.value

        }

    }

    fun setMoviesIntoUi(): LiveData<Resource<List<Daily>>>? {
        return futureWeather1

    }

}
