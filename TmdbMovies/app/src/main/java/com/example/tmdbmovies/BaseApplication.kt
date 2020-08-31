package com.example.tmdbmovies

import android.app.Application
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BaseApplication : Application() {

    lateinit var retrofit: Retrofit

    override fun onCreate() {
        super.onCreate()
        setInstance(this)
        createRetrofit("https://api.themoviedb.org/")
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


    fun getRetrofitt(): Retrofit {
        if (null == retrofit) {
            return createRetrofit("https://api.themoviedb.org/")
        }
        return retrofit
    }

    private fun createRetrofit(baseUrl: String): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        client = OkHttpClient.Builder().addInterceptor(logging)

        retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
        return retrofit
    }

}