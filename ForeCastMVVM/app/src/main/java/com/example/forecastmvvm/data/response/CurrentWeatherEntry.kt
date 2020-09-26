package com.example.forecastmvvm.data.response


import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Weather_Info")
data class CurrentWeatherEntry(
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("is_day")
    val isDay: String,
    @SerializedName("observation_time")
    val observationTime: String,
    val temperature: Double,
    @SerializedName("uv_index")
    val uvIndex: Int,
    val visibility: Int,
    @SerializedName("weather_code")
    val weatherCode: Int,
    @SerializedName("wind_degree")
    val windDegree: Int,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("feelslike")
    val feelslike: Double
) {
    @PrimaryKey(autoGenerate = false)
    var id = 0


    @SerializedName("weather_descriptions")
    @ColumnInfo(name = "weather_descriptions")
    var weatherDescriptions: ArrayList<String>? = null

    @SerializedName("weather_icons")
    @ColumnInfo(name = "weather_icons")
    var weatherIcons: ArrayList<String>? = null

}