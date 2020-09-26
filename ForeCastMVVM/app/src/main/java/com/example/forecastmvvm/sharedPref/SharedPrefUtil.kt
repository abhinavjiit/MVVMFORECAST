package com.example.forecastmvvm.sharedPref

import android.content.Context
import androidx.lifecycle.LiveData

object SharedPrefUtil {


    var longitude: LiveData<Long>? = null
    var latitude: LiveData<Long>? = null


    fun setCountryLongitudeLatitude(
        country: String,
        context: Context,
        longitude: Double,
        latitude: Double, locality: String
    ) {

        val sharedPref =
            context.applicationContext.getSharedPreferences("my_files", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("country", country)
        editor.putLong("longitude", longitude.toLong())
        editor.putLong("latitude", latitude.toLong())
        editor.putString("locality", locality)

        editor.apply()
    }


    fun getLatitude(context: Context): Long {
        val sharedPref =
            context.applicationContext.getSharedPreferences("my_files", Context.MODE_PRIVATE)
        return sharedPref.getLong("latitude", 0L)
    }

    fun getLongitude(context: Context): Long {
        val sharedPref =
            context.applicationContext.getSharedPreferences("my_files", Context.MODE_PRIVATE)
        return sharedPref.getLong("longitude", 0L)
    }

    fun getLocality(context: Context): String? {
        val sharedPref =
            context.applicationContext.getSharedPreferences("my_files", Context.MODE_PRIVATE)
        return sharedPref.getString("locality", null)
    }

    fun getCountry(context: Context): String? {
        val sharedPref =
            context.applicationContext.getSharedPreferences("my_files", Context.MODE_PRIVATE)
        return sharedPref.getString("country", null)
    }

}