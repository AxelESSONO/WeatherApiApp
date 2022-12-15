package com.axel.weatherapplibrary.repository

import com.axel.weatherapplibrary.BuildConfig
import com.axel.weatherapplibrary.api.WeatherApiService
import com.axel.weatherapplibrary.utils.Constants
import javax.inject.Inject

class WeatherRepository
@Inject
constructor(private val weatherApiService: WeatherApiService)
{
    suspend fun getWeather(
        latitude : Double,
        longitude : Double,
        apiKey : String = BuildConfig.WEATHER_API_KEY
    ) = weatherApiService.getWeather(latitude, longitude, apiKey)
}