package com.example.forecastmvvm

import android.app.Application
import com.example.forecastmvvm.data.db.DataBaseClass
import com.example.forecastmvvm.di.AppComponent
import com.example.forecastmvvm.di.CurrentWeatherSubComponent
import com.example.forecastmvvm.di.DaggerAppComponent
import com.example.forecastmvvm.di.Injector
import com.example.forecastmvvm.di.module.AppModule
import com.example.forecastmvvm.di.module.LocalDataModule
import com.example.forecastmvvm.di.module.RemoteDataModule
import com.jakewharton.threetenabp.AndroidThreeTen
import okhttp3.OkHttpClient

class BaseApplication : Application(), Injector {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        setInstance(this)
        appComponent =
            DaggerAppComponent.builder().appModule(AppModule(applicationContext)).remoteDataModule(
                RemoteDataModule()
            ).localDataModule(
                LocalDataModule(
                    DataBaseClass.invoke(applicationContext).weatherDao()
                )
            ).build()

        AndroidThreeTen.init(this);
    }

    companion object {
        @JvmStatic
        lateinit var application: BaseApplication
        lateinit var client: OkHttpClient.Builder
        fun setInstance(application: BaseApplication) {
            BaseApplication.application = application
        }

        fun getInstance(): BaseApplication {
            return application
        }
    }

    override fun createCurrentWeatherComponent(): CurrentWeatherSubComponent {
        return appComponent.currentWeatherSubComponent().create()
    }


}