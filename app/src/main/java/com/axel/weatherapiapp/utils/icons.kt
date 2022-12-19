package com.axel.weatherapiapp.utils

import com.axel.weatherapiapp.R

fun fetchingIcons( icon : String?) : Int = when(icon){
        "01d" ->  R.drawable.clear_sky_day
        "01n" ->  R.drawable.clear_sky_night
        "02d" ->  R.drawable.few_clouds_day
        "02n" ->  R.drawable.few_clouds_night
        "03d" ->  R.drawable.few_clouds_day
        "03n" ->  R.drawable.few_clouds_night
        "04d" ->  R.drawable.overcast_clouds_day
        "04n" ->  R.drawable.overcast_clouds_night
        "09d" ->  R.drawable.rain_clouds_day
        "09n" ->  R.drawable.rain_clouds_night
        "10d" ->  R.drawable.light_rain_day
        "10n" ->  R.drawable.light_rain_night
        "11d" ->  R.drawable.stormy_clouds_day
        "11n" ->  R.drawable.stormy_clouds_night
        "13d" ->  R.drawable.light_snow_day
        "13n" ->  R.drawable.light_snow_night
        "50d" ->  R.drawable.wind_day
        "50n" ->  R.drawable.wind_night
    else -> R.drawable.weather_background
}
