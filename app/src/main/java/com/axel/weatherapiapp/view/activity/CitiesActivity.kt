package com.axel.weatherapiapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.axel.weatherapiapp.R
import com.axel.weatherapiapp.databinding.ActivityCitiesBinding
import com.axel.weatherapiapp.view.adapter.CityAdapter
import com.axel.weatherapiapp.view.adapter.HourlyAdapter
import com.axel.weatherapplibrary.model.CityWeather
import com.axel.weatherapplibrary.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CitiesActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCitiesBinding
    private lateinit var weatherViewModel: WeatherViewModel
    private  var cities: List<CityWeather>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cities)
        binding.comeBack.setOnClickListener { onBackPressed() }

        Thread{
            cities = weatherViewModel.getAll(applicationContext)
            runOnUiThread {
                binding.noCitySavedYet.visibility = if (cities?.isEmpty() == true) View.VISIBLE  else View.GONE
                binding.cityRecycler.apply {
                    adapter = cities?.let { CityAdapter(it) }
                    layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                }

                for (city: CityWeather in cities!!){
                    Log.d("NDZOGOMVE", "city : ${city.name}")
                    Log.d("NDZOGOMVE", "lat : ${city.lat}")
                    Log.d("NDZOGOMVE", "lon : ${city.lon}")
                    Log.d("NDZOGOMVE", "****************")
                }

            }
        }.start()
    }
}