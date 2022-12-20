package com.axel.weatherapplibrary.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axel.weatherapplibrary.dao.CityWeatherDao
import com.axel.weatherapplibrary.database.CityWeatherDatabase
import com.axel.weatherapplibrary.model.CityWeather
import com.axel.weatherapplibrary.model.WeatherRoot
import com.axel.weatherapplibrary.repository.CityWeatherRepository
import com.axel.weatherapplibrary.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) : ViewModel(){

    private val _response = MutableLiveData<WeatherRoot>()
    val weatherResponse : LiveData<WeatherRoot>
        get() = _response

    private fun getWeather(latitude : Double, longitude : Double) = viewModelScope.launch {
        weatherRepository.getWeather(latitude  = latitude, longitude  = longitude).let { response ->
            if (response.isSuccessful){
                _response.postValue(response.body())
            }else{
                Log.d("WEATHER", "Error Response Axel : ${response.message()}")
            }
        }
    }

    fun setWeather(latitude : Double, longitude : Double) : LiveData<WeatherRoot> {
        getWeather(latitude, longitude)
        return weatherResponse
    }

    fun retreiveWeather(latitude : Double, longitude : Double){

    }

     fun addCity(context: Context, weather: CityWeather){
        val thread = Thread{
            val dao : CityWeatherDao  = CityWeatherDatabase.getDataBase(context).cityWeatherDao()
            val cityWeatherRepository = CityWeatherRepository(dao)
            cityWeatherRepository.insertCity(weather)
        }.start()
    }

     fun getAll(context: Context) : MutableList<CityWeather>? {
            CityWeatherDatabase.getDataBase(context).cityWeatherDao().getAll()
            val dao : CityWeatherDao  = CityWeatherDatabase.getDataBase(context).cityWeatherDao()
            val cityWeatherRepository = CityWeatherRepository(dao)

        return cityWeatherRepository.getAllCity()
    }

     fun deleteCity(context: Context, weather: CityWeather){
         val thread = Thread{
             val dbInstance = CityWeatherDatabase.getDataBase(context)
             val dao = dbInstance.cityWeatherDao()
             val cityWeatherRepository = CityWeatherRepository(dao)
             cityWeatherRepository.delete(weather)

         }.start()
    }
}