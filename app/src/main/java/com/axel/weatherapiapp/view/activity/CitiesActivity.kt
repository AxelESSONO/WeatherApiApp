package com.axel.weatherapiapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.axel.weatherapiapp.R
import com.axel.weatherapiapp.databinding.ActivityCitiesBinding
import com.axel.weatherapiapp.view.adapter.CityAdapter
import com.axel.weatherapplibrary.model.CityWeather
import com.axel.weatherapplibrary.viewmodel.WeatherViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CitiesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCitiesBinding
    private lateinit var weatherViewModel: WeatherViewModel
    private var cities: MutableList<CityWeather>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cities)
        binding.comeBack.setOnClickListener { onBackPressed() }

        Thread {
            cities = weatherViewModel.getAll(applicationContext)
            runOnUiThread {
                binding.noCitySavedYet.visibility =
                    if (cities?.isEmpty() == true) View.VISIBLE else View.GONE
                binding.cityRecycler.apply {
                    adapter = cities?.let {
                        CityAdapter(this@CitiesActivity, weatherViewModel, it) {
                            cities!!.remove(it)
                            weatherViewModel.deleteCity(applicationContext, it)
                            binding.cityRecycler.adapter?.notifyItemRemoved(getPosition(it))
                            binding.noCitySavedYet.visibility = if (cities?.isEmpty() == true) View.VISIBLE else View.GONE
                            Snackbar.make(binding.root,"${it.name} was deleted", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                }
            }
        }.start()

    }

    private fun getPosition(cityWeather: CityWeather) : Int {
        for (city in 0 until cities!!.size){
            if (city.equals(cityWeather)){
                return city
            }
        }
        return -1
    }

}