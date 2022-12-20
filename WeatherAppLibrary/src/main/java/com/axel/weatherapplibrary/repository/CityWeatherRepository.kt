package com.axel.weatherapplibrary.repository

import androidx.lifecycle.LiveData
import com.axel.weatherapplibrary.dao.CityWeatherDao
import com.axel.weatherapplibrary.model.CityWeather

class CityWeatherRepository(private val cityWeatherDao: CityWeatherDao) {
     fun insertCity(cityWeather: CityWeather) = cityWeatherDao.insertCity(cityWeather)
     fun delete(cityWeather: CityWeather) = cityWeatherDao.delete(cityWeather)
     fun getAllCity() : MutableList<CityWeather> = cityWeatherDao.getAll()
}