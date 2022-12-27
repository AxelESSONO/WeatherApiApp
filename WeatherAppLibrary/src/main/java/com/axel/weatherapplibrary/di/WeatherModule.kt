package com.axel.weatherapplibrary.di

import android.app.Application
import com.axel.weatherapplibrary.database.CityWeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Singleton
    @Provides
    fun getAppDB(context: Application) : CityWeatherDatabase = CityWeatherDatabase.getDataBase(context)

    @Singleton
    @Provides
    fun getAppDao(cityWeatherDatabase: CityWeatherDatabase) = cityWeatherDatabase.cityWeatherDao()

}