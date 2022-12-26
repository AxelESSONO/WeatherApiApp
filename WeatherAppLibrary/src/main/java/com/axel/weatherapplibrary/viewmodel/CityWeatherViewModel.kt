package com.axel.weatherapplibrary.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axel.weatherapplibrary.model.CityWeather
import com.axel.weatherapplibrary.repository.CityWeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityWeatherViewModel @Inject constructor(private val cityWeatherRepository: CityWeatherRepository) : ViewModel() {

    fun addCity(context: Context, cityWeather: CityWeather) = viewModelScope.launch {
        cityWeatherRepository.insertCity(cityWeather)
    }

    suspend fun getAll(context: Context) : MutableList<CityWeather>? {
        return cityWeatherRepository.getAllCity()
    }

    fun deleteCity(context: Context, cityWeather: CityWeather) = viewModelScope.launch {
        cityWeatherRepository.delete(cityWeather)
    }
}