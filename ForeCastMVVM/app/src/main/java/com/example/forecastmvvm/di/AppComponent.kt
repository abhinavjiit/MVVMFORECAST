package com.example.forecastmvvm.di

import com.example.forecastmvvm.di.module.AppModule
import com.example.forecastmvvm.di.module.LocalDataModule
import com.example.forecastmvvm.di.module.RemoteDataModule
import com.example.forecastmvvm.di.module.RepoModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RepoModule::class, RemoteDataModule::class, LocalDataModule::class])
interface AppComponent {
    fun currentWeatherSubComponent(): CurrentWeatherSubComponent.Factory
}