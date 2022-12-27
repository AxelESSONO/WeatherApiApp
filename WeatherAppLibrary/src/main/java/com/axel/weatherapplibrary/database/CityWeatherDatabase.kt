package com.axel.weatherapplibrary.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.axel.weatherapplibrary.dao.CityWeatherDao
import com.axel.weatherapplibrary.model.CityWeather

@Database(entities = [CityWeather::class], version = 1, exportSchema = false)
abstract class CityWeatherDatabase : RoomDatabase() {
    abstract fun cityWeatherDao(): CityWeatherDao

    companion object{

        @Volatile
        private var INSTANCE : CityWeatherDatabase? = null

        fun getDataBase(context: Context) : CityWeatherDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CityWeatherDatabase::class.java,
                    "database-name").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}