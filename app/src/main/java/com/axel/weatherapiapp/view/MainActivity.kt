package com.axel.weatherapiapp.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.axel.weatherapiapp.R
import com.axel.weatherapiapp.databinding.ActivityMainBinding
import com.axel.weatherapplibrary.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        weatherViewModel.weatherResponse.observe(this) { weatherResponse ->
            binding.apply {
                weatherText.text = weatherResponse.timezone
            }
        }
    }
}