package com.axel.weatherapiapp.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)



        var myCurrent : Current?
        binding.currentDate = getCurrentDate()
        weatherViewModel.weatherResponse.observe(this) { weatherResponse ->
            binding.apply {
                weatherRoot = weatherResponse
                imageUrl = fetchingIcons(weatherResponse.current?.weather?.get(0)?.icon)
                weather = weatherResponse.current?.weather?.get(0)
                myCurrent = weatherResponse?.current

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
                    layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false )
                }

                dailyRecyclerView.apply {
                    adapter = HourlyAdapter(weatherResponse.hourly)
                    layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false )
                }
            }
        }

        binding.seeCities.setOnClickListener {
            Toast.makeText(applicationContext, "See all cities", Toast.LENGTH_SHORT).show()
        }

        binding.addNewCity.setOnClickListener {
            val addCityDialogFragment = AddCityDialogFragment()
            addCityDialogFragment.show(supportFragmentManager, "CITY_DIALOG")
        }

    }

    private fun getCurrentDate() = DateFormat
        .getDateInstance()
        .format(Calendar.getInstance().time)

}