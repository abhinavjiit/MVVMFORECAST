package com.example.forecastmvvm.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.forecastmvvm.data.response.CurrentWeatherEntry


@Dao
interface WeatherDataBaseDaoInterface {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(currentWeatherEntry: CurrentWeatherEntry)

    @Query("SELECT * FROM Weather_Info")
    fun getCurrentWeather(): CurrentWeatherEntry?

}

