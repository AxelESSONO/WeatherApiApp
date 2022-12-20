package com.axel.weatherapiapp.view.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.axel.weatherapiapp.R
import com.axel.weatherapiapp.databinding.ActivityMainBinding
import com.axel.weatherapiapp.utils.FormatDate
import com.axel.weatherapiapp.utils.UnixTimeUtils
import com.axel.weatherapiapp.utils.fetchingIcons
import com.axel.weatherapiapp.view.adapter.HourlyAdapter
import com.axel.weatherapiapp.view.fragment.AddCityDialogFragment
import com.axel.weatherapplibrary.model.Current
import com.axel.weatherapplibrary.viewmodel.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.text.DateFormat
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val weatherViewModel: WeatherViewModel by viewModels()
    private lateinit var locationManager: LocationManager
    private var myCurrent: Current? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        requestPermissions()

        binding.currentDate = getCurrentDate()

        binding.seeCities.setOnClickListener {
            startActivity(Intent(this, CitiesActivity::class.java))
        }

        binding.addNewCity.setOnClickListener {
            val addCityDialogFragment = AddCityDialogFragment()
            addCityDialogFragment.show(supportFragmentManager.beginTransaction(), "CITY_DIALOG")
        }

    }

    private fun requestPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { pLocation ->
                if (pLocation != null) {
                    val geocoder: Geocoder = Geocoder(this@MainActivity, Locale.getDefault())
                    var addresses = mutableListOf<Address>()
                    try {
                        addresses = geocoder.getFromLocation(pLocation.latitude, pLocation.longitude, 1) as MutableList<Address>
                        binding.cityName = addresses[0].locality
                        bindAllData(addresses[0].latitude, addresses[0].longitude)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        } else {
            Toast.makeText(applicationContext, "Device'location was permitted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentDate() = DateFormat.getDateInstance().format(Calendar.getInstance().time)

    private fun bindAllData(latitude: Double, longitude: Double) {
        weatherViewModel.setWeather(latitude, longitude).observe(this) { weatherResponse ->
            binding.apply {
                weatherRoot = weatherResponse
                imageUrl = fetchingIcons(weatherResponse.current?.weather?.get(0)?.icon)
                weather = weatherResponse.current?.weather?.get(0)
                myCurrent = weatherResponse?.current
                cityName = cityName

                current = myCurrent
                val unixTimeUtils: UnixTimeUtils = UnixTimeUtils()
                sunrise = unixTimeUtils.format(
                    weatherResponse.current?.sunrise?.let { unixTimeUtils.fromTimestamp(it.toLong()) },
                    FormatDate.HOUR_MIN
                )
                sunset = unixTimeUtils.format(
                    weatherResponse.current?.sunset?.let { unixTimeUtils.fromTimestamp(it.toLong()) },
                    FormatDate.HOUR_MIN
                )

                hourlyRecyclerView.apply {
                    adapter = HourlyAdapter(weatherResponse.hourly)
                    layoutManager = LinearLayoutManager(
                        applicationContext,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                }

                dailyRecyclerView.apply {
                    adapter = HourlyAdapter(weatherResponse.hourly)
                    layoutManager = LinearLayoutManager(
                        applicationContext,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                }
            }
        }
    }

    companion object {
        const val REQUEST_LOCATION = 1
    }
}