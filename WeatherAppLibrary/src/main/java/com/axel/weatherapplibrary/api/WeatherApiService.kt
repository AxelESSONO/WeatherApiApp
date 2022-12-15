package com.axel.weatherapplibrary.api

import com.axel.weatherapplibrary.model.WeatherRoot
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("onecall")
    suspend fun getWeather(
        @Query("lat") latitude : Double,
        @Query("lon") longitude : Double,
        @Query("appid") apiKey : String
    ) : Response<WeatherRoot>

}