package com.axel.weatherapplibrary.repository

import com.axel.weatherapplibrary.api.WeatherApiService
import javax.inject.Inject

class WeatherRepository
@Inject
constructor(private val weatherApiService: WeatherApiService)
{
    suspend fun getWeather(
        latitude : Double,
        longitude : Double,
        apiKey : String
    ) = weatherApiService.getWeather(latitude, longitude, apiKey)
}