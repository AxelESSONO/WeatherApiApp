package com.axel.weatherapplibrary.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axel.weatherapplibrary.model.WeatherRoot
import com.axel.weatherapplibrary.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
@Inject
constructor(private val weatherRepository: WeatherRepository) : ViewModel(){
    private val _response = MutableLiveData<WeatherRoot>()
    val weatherResponse : LiveData<WeatherRoot>
        get() = _response

    init {
        getWeather()
    }

    private fun getWeather() = viewModelScope.launch {
        weatherRepository.getWeather().let { response ->
            if (response.isSuccessful){
                _response.postValue(response.body())
            }else{
                Log.d("WEATHER", "Error Response : ${response.message()}")
            }
        }
    }
}