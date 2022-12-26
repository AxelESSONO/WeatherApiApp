package com.axel.weatherapplibrary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axel.weatherapplibrary.model.CityWeather
import com.axel.weatherapplibrary.repository.CityWeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityWeatherViewModel @Inject constructor(private val cityWeatherRepository: CityWeatherRepository) : ViewModel() {

    fun addCity(cityWeather: CityWeather) = viewModelScope.launch {
        cityWeatherRepository.insertCity(cityWeather)
    }

    suspend fun getAll() : MutableList<CityWeather>? {
        return cityWeatherRepository.getAllCity()
    }

    fun deleteCity(cityWeather: CityWeather) = viewModelScope.launch {
        cityWeatherRepository.delete(cityWeather)
    }
}