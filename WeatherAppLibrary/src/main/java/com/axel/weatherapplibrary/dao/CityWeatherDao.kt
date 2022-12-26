package com.axel.weatherapplibrary.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.axel.weatherapplibrary.model.CityWeather

@Dao
interface CityWeatherDao {

    @Insert
    suspend fun insertCity(cityWeather: CityWeather)

    @Query("SELECT * FROM city")
    suspend fun getAll(): MutableList<CityWeather>

    @Delete
    suspend fun delete(user: CityWeather)

}