package com.axel.weatherapplibrary.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class CityWeather(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "lat") val lat: Double?,
    @ColumnInfo(name = "lon") val lon: Double?,
    @ColumnInfo(name = "temp") val temp: Double?,
    @ColumnInfo(name = "icon") val icon: String?,
)
