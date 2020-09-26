package com.example.forecastmvvm.ui.fragment.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.forecastmvvm.data.network.repo.CurrentWeatherRepoInterface

@Suppress("UNCHECKED_CAST")
class CurrentViewModelFactory(private val currentWeatherRepoInterface: CurrentWeatherRepoInterface) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(currentWeatherRepoInterface) as T
    }

}