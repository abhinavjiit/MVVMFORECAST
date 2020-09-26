package com.example.forecastmvvm.ui.fragment.current

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.forecastmvvm.BaseApplication
import com.example.forecastmvvm.BaseApplication.Companion.application
import com.example.forecastmvvm.R
import com.example.forecastmvvm.Resource
import com.example.forecastmvvm.data.response.Hourly
import com.example.forecastmvvm.di.Injector
import com.example.forecastmvvm.sharedPref.SharedPrefUtil
import com.example.forecastmvvm.ui.activity.REQUEST_LOCATION_PERMISSION
import com.example.forecastmvvm.ui.adapter.CurrentWeatherHourlyAdapter
import com.example.forecastmvvm.ui.fragment.BaseFragment
import com.google.android.gms.location.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.current_weather_fragment.*
import java.util.*
import javax.inject.Inject

class CurrentWeatherFragment : BaseFragment() {

    companion object {
        fun newInstance() =
            CurrentWeatherFragment()
    }

    @Inject
    lateinit var factory: CurrentViewModelFactory


    private var country: String? = null
    private var locality: String? = null
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mSettingsClient: SettingsClient? = null
    private var mLocationRequest: LocationRequest? = null
    private var mLocationCallback: LocationCallback? = null
    private var mCurrentLocation: Location? = null

    private lateinit var viewModel: CurrentWeatherViewModel

    @BindView(R.id.countryName)
    lateinit var countryName: TextView

    @BindView(R.id.textView_condition)
    lateinit var textView_condition: TextView

    @BindView(R.id.textView_temperature)
    lateinit var textView_temperature: TextView

    @BindView(R.id.imageView_condition_icon)
    lateinit var imageView_condition_icon: ImageView

    @BindView(R.id.textView_wind)
    lateinit var textView_wind: TextView

    @BindView(R.id.textView_visibility)
    lateinit var textView_visibility: TextView

    @BindView(R.id.textView_precipitation)
    lateinit var textView_precipitation: TextView

    @BindView(R.id.recycler)
    lateinit var recycler: RecyclerView

    private var futListWet: List<Hourly>? = null

    private val adapter: CurrentWeatherHourlyAdapter by lazy {
        CurrentWeatherHourlyAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.current_weather_fragment, container, false)
        ButterKnife.bind(this, view)
        (application as Injector).createCurrentWeatherComponent()
            .inject(this)
        recycler.isNestedScrollingEnabled = false
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter
        adapter.setListRes(futureListData = futListWet)
        adapter.notifyDataSetChanged()
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(requireActivity(), factory).get(CurrentWeatherViewModel::class.java)
        if (isLocationEnabled() && SharedPrefUtil.getCountry(BaseApplication.getInstance()) == null) {
            showProgressDialog("please wait")
            fetchCurrentLocationData()

        } else {
            initilizeViewModel()
        }
    }


    private fun initilizeViewModel() {
        if (isOnline()) {
            viewModel.getWeather().observe(requireActivity(), androidx.lifecycle.Observer {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        it.data?.let { currentWeatherEntry ->
                            Log.d("TAg", currentWeatherEntry.isDay)
                            Toast.makeText(activity, "Fetched Data", Toast.LENGTH_SHORT).show()
                            textView_condition.text =
                                "description ".plus(currentWeatherEntry.weatherDescriptions?.get(0))
                            textView_temperature.text = (currentWeatherEntry.temperature.toString())
                            textView_feels_like_temperature.text =
                                "FeelsLike :".plus(currentWeatherEntry.feelslike.toString())
                            textView_wind.text =
                                "Wind :".plus(currentWeatherEntry.windDir.plus("," + currentWeatherEntry.windSpeed.toString()))
                            textView_visibility.text =
                                "Visibility :".plus(currentWeatherEntry.visibility.toString())
                            textView_precipitation.text =
                                "Humidity :".plus(currentWeatherEntry.humidity.toString())

                            Picasso.get().load(currentWeatherEntry.weatherIcons?.get(0))
                                .into(imageView_condition_icon)
                            countryName.text =
                                SharedPrefUtil.getCountry(BaseApplication.getInstance())
                        }

                    }

                    Resource.Status.ERROR -> {
                        Toast.makeText(
                            requireActivity(),
                            "make sure your location and net is enabled",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                    else
                    -> {
                    }
                }
            })

            viewModel.getHourlyWeather().observe(requireActivity(), androidx.lifecycle.Observer {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        adapter.setListRes(it.data)
                        adapter.notifyDataSetChanged()

                    }
                    Resource.Status.ERROR
                    -> {
                        adapter.setListRes(null)
                        adapter.notifyDataSetChanged()
                        Toast.makeText(
                            requireActivity(),
                            "some thing went wrong",
                            Toast.LENGTH_SHORT
                        ).show()

                    }


                }

            })
        } else {
            viewModel.getWeatherFromLocal().observe(requireActivity(), androidx.lifecycle.Observer {
                Log.d("TAg", it.isDay)
                Toast.makeText(activity, "Fetched Data", Toast.LENGTH_SHORT).show()
                textView_condition.text = "description ".plus(it.weatherDescriptions?.get(0))
                textView_temperature.text = (it.temperature.toString())
                textView_feels_like_temperature.text = "FeelsLike :".plus(it.feelslike.toString())
                textView_wind.text =
                    "Wind :".plus(it.windDir.plus("," + it.windSpeed.toString()))
                textView_visibility.text = "Visibility :".plus(it.visibility.toString())
                textView_precipitation.text = "Humidity :".plus(it.humidity.toString())
                Picasso.get().load(it.weatherIcons?.get(0))
                    .into(imageView_condition_icon)
                countryName.text = SharedPrefUtil.getCountry(BaseApplication.getInstance())
            })


        }


        /*  viewModel.setLatestCurrentWeather()
              ?.observe(requireActivity(), androidx.lifecycle.Observer {
                  when (it.status) {
                      Resource.Status.SUCCESS -> {
                          removeProgressDialog()
                          Toast.makeText(activity, "Fetched Data", Toast.LENGTH_SHORT).show()
                          textView_condition.text = it.data?.weatherDescriptions?.get(0)
                          textView_temperature.text = it.data?.temperature.toString()
                          textView_feels_like_temperature.text = it.data?.feelslike.toString()
                          textView_wind.text =
                              it.data?.windDir.plus("," + it.data?.windSpeed.toString())
                          textView_visibility.text = it.data?.visibility.toString()
                          textView_precipitation.text = it.data?.humidity.toString()

                          Picasso.get().load(it.data?.weatherIcons?.get(0))
                              .into(imageView_condition_icon)
                          countryName.text = SharedPrefUtil.getCountry(BaseApplication.getInstance())

                      }
                      Resource.Status.ERROR -> {
                          removeProgressDialog()

                      }
                      else -> {
                      }
                  }
              })*/


    }


    private fun fetchCurrentLocationData() {
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                mCurrentLocation = locationResult?.lastLocation
                Log.d(
                    "b",
                    mCurrentLocation?.latitude.toString() + "," + mCurrentLocation?.longitude
                )
                getCountryName(mCurrentLocation?.latitude!!, mCurrentLocation?.longitude!!)
                country?.let { country ->
                    countryName.text = country
                    locality?.let { locality ->
                        SharedPrefUtil.setCountryLongitudeLatitude(
                            country,
                            latitude = mCurrentLocation?.latitude!!,
                            locality = locality,
                            longitude = mCurrentLocation?.longitude!!,
                            context = BaseApplication.getInstance()
                        )
                    }
                }
                if (isAdded) {
                    removeProgressDialog()
                    initilizeViewModel()
                    // viewModel.getLatestCurrentWeather()
                }


            }
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        mSettingsClient = LocationServices.getSettingsClient(requireActivity())
        mLocationRequest = LocationRequest()
        mLocationRequest?.let {
            it.interval = 15 * 1000 * 60
            it.fastestInterval = 1000
            it.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_LOCATION_PERMISSION
                    )
                } else {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_LOCATION_PERMISSION
                    )
                }
            } else {
                LocationServices.getFusedLocationProviderClient(requireActivity())
                    .requestLocationUpdates(
                        mLocationRequest, mLocationCallback,
                        Looper.getMainLooper()
                    )
            }

        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) {
                if ((ContextCompat.checkSelfPermission(
                        requireActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) ==
                            PackageManager.PERMISSION_GRANTED)
                ) {
                    Toast.makeText(requireActivity(), "Permission Granted", Toast.LENGTH_SHORT)
                        .show()
                    LocationServices.getFusedLocationProviderClient(requireActivity())
                        .requestLocationUpdates(
                            mLocationRequest, mLocationCallback,
                            Looper.getMainLooper()
                        )

                }
            } else {
                Toast.makeText(requireActivity(), "Permission Denied", Toast.LENGTH_SHORT).show()
            }
            return
        }


    }


    private fun isOnline(): Boolean {
        val connectivityManager =
            BaseApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetwork
        val actNw =
            connectivityManager.getNetworkCapabilities(networkInfo) ?: return false
        return actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) && actNw.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_VALIDATED
        )
    }


    private fun getCountryName(latitude: Double, longitude: Double) {
        if (isAdded) {
            val geoCoder = Geocoder(requireActivity(), Locale.getDefault())
            val address: List<Address>?
            try {
                address = geoCoder.getFromLocation(latitude, longitude, 1)
                if (address != null && address.isNotEmpty()) {
                    country = address[0].countryName
                    locality = address[0].locality
                }
            } catch (e: Exception) {

            }
        }


    }


    private fun isLocationEnabled(): Boolean {
        val lm = BaseApplication.getInstance()
            .getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
}
