package com.axel.weatherapplibrary.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axel.weatherapplibrary.model.CityWeather
import com.axel.weatherapplibrary.repository.CityWeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityWeatherViewModel @Inject constructor(private val cityWeatherRepository: CityWeatherRepository) : ViewModel() {

    val cities = cityWeatherRepository.cities
    private val _response = MutableLiveData<CityWeather>()
    val cityResponse : LiveData<CityWeather>
        get() = _response

    private val _responseAllCities = MutableLiveData<MutableList<CityWeather>>()
    var citiesResponse : LiveData<MutableList<CityWeather>> = _responseAllCities

    private fun addCity(cityWeather: CityWeather) = viewModelScope.launch {
        cityWeatherRepository.insertCity(cityWeather)
    }

    fun deleteCity(cityWeather: CityWeather) = viewModelScope.launch {
        cityWeatherRepository.delete(cityWeather)
    }

    fun saveCity(cityWeather: CityWeather) : LiveData<CityWeather>{
        addCity(cityWeather)
        return cityResponse
    }

}