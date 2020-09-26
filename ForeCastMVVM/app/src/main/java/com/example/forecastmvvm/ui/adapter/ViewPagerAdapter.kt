package com.example.forecastmvvm.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.forecastmvvm.ui.fragment.current.CurrentWeatherFragment
import com.example.forecastmvvm.ui.fragment.future.FutureListWeatherFragment

class ViewPagerAdapter(fm: FragmentManager,private val country: String?) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putString("country", country)
        return when (position) {
            0 -> {
                val currentWeatherFragment = CurrentWeatherFragment()
                currentWeatherFragment.arguments = bundle
                currentWeatherFragment
            }
            else -> {
                val futureListWeatherFragment = FutureListWeatherFragment()
                futureListWeatherFragment.arguments = bundle
                futureListWeatherFragment
            }

        }


    }

    override fun getCount(): Int {
        return 2
    }
}