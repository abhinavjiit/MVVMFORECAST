package com.example.forecastmvvm.di.module

import android.content.Context
import com.example.forecastmvvm.di.CurrentWeatherSubComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = [CurrentWeatherSubComponent::class])
class AppModule(val context: Context) {

    @Singleton
    @Provides
    fun provideApplicationContext(): Context {
        return context.applicationContext
    }

}