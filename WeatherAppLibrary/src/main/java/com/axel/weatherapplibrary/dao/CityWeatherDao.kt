package com.axel.weatherapplibrary.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.axel.weatherapplibrary.model.CityWeather
import dagger.Provides

@Dao
interface CityWeatherDao {

    @Insert
    suspend fun insertCity(cityWeather: CityWeather)

    @Query("SELECT * FROM city")
    fun getAll(): LiveData<MutableList<CityWeather>>

    @Delete
    suspend fun delete(user: CityWeather)

}