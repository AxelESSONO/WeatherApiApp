package com.axel.weatherapplibrary.repository

import androidx.lifecycle.LiveData
import com.axel.weatherapplibrary.dao.CityWeatherDao
import com.axel.weatherapplibrary.model.CityWeather
import javax.inject.Inject

class CityWeatherRepository
@Inject
constructor(private val cityWeatherDao: CityWeatherDao)
{

     val cities = cityWeatherDao.getAll()
     suspend fun insertCity(cityWeather: CityWeather) = cityWeatherDao.insertCity(cityWeather)
     suspend fun delete(cityWeather: CityWeather) = cityWeatherDao.delete(cityWeather)
     suspend fun getAllCity() : LiveData<MutableList<CityWeather>> = cityWeatherDao.getAll()
}