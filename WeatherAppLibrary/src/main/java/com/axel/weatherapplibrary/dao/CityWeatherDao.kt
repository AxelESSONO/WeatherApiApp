package com.axel.weatherapplibrary.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.axel.weatherapplibrary.model.CityWeather

@Dao
interface CityWeatherDao {

    @Insert
    fun insertCity(cityWeather: CityWeather)

    @Query("SELECT * FROM city")
    fun getAll(): MutableList<CityWeather>

    @Delete
    fun delete(user: CityWeather)

}