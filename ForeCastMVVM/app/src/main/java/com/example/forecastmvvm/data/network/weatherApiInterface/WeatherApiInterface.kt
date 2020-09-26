package com.example.forecastmvvm.data.network.weatherApiInterface

import com.example.forecastmvvm.data.response.CurrentWeatherResponse
import com.example.forecastmvvm.data.response.FutureWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "2f2a23b5900692e87480afea8c2ccb26"
const val API_KEY2 = "0e392b8c67d137fcff5801eb74ccaaff"

//http://api.weatherstack.com/current?access_key=2f2a23b5900692e87480afea8c2ccb26&query=New%20York
interface WeatherApiInterface {

    @GET("/current")
    suspend fun getCurrentWeatherAsync(@Query("query") location: String): CurrentWeatherResponse

    // https://api.apixu.com/v1/forecast.json?key=2f2a23b5900692e87480afea8c2ccb26&q=Los%20Angeles&days=1
    @GET("forecast")
    fun getFutureWeatherAsync(
        @Query("query") location: String,
        @Query("forecast_days") days: Int,
        @Query("lang") languageCode: String = "en"
    ): Deferred<CurrentWeatherResponse>


    //https://api.openweathermap.org/data/2.5/onecall?lat=26.4605&lon=79.5113&appid=0e392b8c67d137fcff5801eb74ccaaff
    @GET("/data/2.5/onecall")
    suspend fun getForecastData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,// @Query("exclude") exclude: String,
        @Query("appid") appid: String
    ): FutureWeatherResponse

    companion object {
        operator fun invoke(baseUrl: String): WeatherApiInterface {
            val interceptor = Interceptor { chain ->
                val url = chain.request().url.newBuilder()
                    .addQueryParameter("access_key", API_KEY).build()


                val request = chain.request().newBuilder().url(url).build()
                return@Interceptor chain.proceed(request)
            }
            val httpLogging = HttpLoggingInterceptor()
            httpLogging.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient =
                OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(httpLogging)
                    .build()

            val retrofit = retrofitInstance(okHttpClient, baseUrl)

            return retrofit
        }
//"http://api.weatherstack.com"

        private fun retrofitInstance(
            okHttpClient: OkHttpClient,
            baseUrl: String
        ): WeatherApiInterface {

            return Retrofit.Builder().client(okHttpClient).baseUrl(baseUrl)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(WeatherApiInterface::class.java)

        }

    }


}