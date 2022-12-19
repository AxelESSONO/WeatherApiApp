package com.axel.weatherapplibrary.api

import com.axel.weatherapplibrary.BuildConfig
import com.axel.weatherapplibrary.model.WeatherRoot
import com.axel.weatherapplibrary.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("onecall")
    suspend fun getWeather(
        @Query("lat") latitude : Double = Constants.DEFAULT_LAT,
        @Query("lon") longitude : Double =  Constants.DEFAULT_LON,
        @Query("appid") apiKey : String = BuildConfig.WEATHER_API_KEY,
        @Query("units") units : String = "metric"
    ) : Response<WeatherRoot>

}