package com.example.forecastmvvm.di

interface Injector {
    fun createCurrentWeatherComponent(): CurrentWeatherSubComponent
}