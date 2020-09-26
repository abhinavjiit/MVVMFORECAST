package com.example.forecastmvvm.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.forecastmvvm.R
import com.example.forecastmvvm.data.response.Hourly
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.future_list_weather_adapter_layout.view.*

class CurrentWeatherHourlyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var futureWeatherList: List<Hourly>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.future_list_weather_adapter_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (futureWeatherList == null)
            0 else
            futureWeatherList?.size!!
    }


    fun setListRes(futureListData: List<Hourly>?) {
        futureWeatherList = futureListData
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.apply {
                date.text = futureWeatherList?.get(position)?.dt.toString().changeToDate()
                humidity.text =
                    "humidity ".plus(futureWeatherList?.get(position)?.humidity.toString())//http://openweathermap.org/img/wn/11d@2x.png
                Picasso.get().load(
                    "http://openweathermap.org/img/wn/${futureWeatherList?.get(position)?.weather?.get(
                        0
                    )?.icon}@2x.png"
                ).into(weatherIcon)
                discription.text =
                    futureWeatherList?.get(position)?.weather?.get(0)?.description.toString()
            }


        }


    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal val date: TextView = view.date
        internal val discription: TextView = view.discription
        internal val humidity: TextView = view.humidity
        internal val weatherIcon: ImageView = view.weatherIcon
        internal val max: TextView = view.max
        internal val min: TextView = view.min

        init {
            min.visibility = View.GONE
            max.visibility = View.GONE
        }
    }
}

