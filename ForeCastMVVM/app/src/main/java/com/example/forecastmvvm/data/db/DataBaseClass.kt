package com.example.forecastmvvm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.forecastmvvm.data.response.CurrentWeatherEntry

@Database(entities = [CurrentWeatherEntry::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DataBaseClass : RoomDatabase() {
    abstract fun weatherDao(): WeatherDataBaseDaoInterface

    companion object {
        @Volatile
        private var instance: DataBaseClass? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {

            instance ?: buildDataBase(context).apply {
                instance = this
            }
        }

        private fun buildDataBase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            DataBaseClass::class.java,
            "forecast_db"
        ).build()
    }
}