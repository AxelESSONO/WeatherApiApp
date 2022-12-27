package com.axel.weatherapiapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.axel.weatherapiapp.R
import com.axel.weatherapiapp.databinding.ActivityCitiesBinding
import com.axel.weatherapiapp.view.adapter.CityAdapter
import com.axel.weatherapplibrary.model.CityWeather
import com.axel.weatherapplibrary.viewmodel.CityWeatherViewModel
import com.axel.weatherapplibrary.viewmodel.WeatherViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class CitiesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCitiesBinding
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var cityWeatherViewModel: CityWeatherViewModel
    private var cities: MutableList<CityWeather>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        cityWeatherViewModel = ViewModelProvider(this)[CityWeatherViewModel::class.java]

        binding = DataBindingUtil.setContentView(this, R.layout.activity_cities)
        binding.comeBack.setOnClickListener { onBackPressed() }

        binding.noCitySavedYet.visibility = View.VISIBLE

        cityWeatherViewModel.cities.observe(this, Observer { citiesResponse ->

            binding.cityRecycler.apply {
                binding.noCitySavedYet.visibility = if (citiesResponse?.isEmpty() == true){
                    Log.d("NDZOGOMVE", "No city")
                    View.VISIBLE
                } else {
                    Log.d("NDZOGOMVE", "Some city")
                    cities = citiesResponse
                    View.GONE
                }

                layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                adapter = CityAdapter(citiesResponse) { cityWeather ->
                    cities!!.remove(cityWeather)
                    cityWeatherViewModel.deleteCity(cityWeather)
                    binding.cityRecycler.adapter?.notifyItemRemoved(getPosition(cityWeather))
                    binding.noCitySavedYet.visibility = if (cities?.isEmpty() == true) View.VISIBLE else View.GONE
                    Snackbar.make(binding.root, "${cityWeather.name} was deleted", Snackbar.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun getPosition(cityWeather: CityWeather): Int {
        for (city in 0 until cities!!.size) {
            if (city.equals(cityWeather)) {
                return city
            }
        }
        return -1
    }
}