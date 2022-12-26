package com.axel.weatherapplibrary.repository

import com.axel.weatherapplibrary.dao.CityWeatherDao
import com.axel.weatherapplibrary.model.CityWeather
import javax.inject.Inject

class CityWeatherRepository
@Inject constructor(private val cityWeatherDao: CityWeatherDao)
{
     suspend fun insertCity(cityWeather: CityWeather) = cityWeatherDao.insertCity(cityWeather)
     suspend fun delete(cityWeather: CityWeather) = cityWeatherDao.delete(cityWeather)
     suspend fun getAllCity() : MutableList<CityWeather> = cityWeatherDao.getAll()
}