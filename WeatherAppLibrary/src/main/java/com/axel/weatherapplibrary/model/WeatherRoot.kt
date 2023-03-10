package com.axel.weatherapplibrary.model


import com.google.gson.annotations.SerializedName

data class WeatherRoot(
    @SerializedName("current")
    var current: Current?,
    @SerializedName("daily")
    var daily: List<Daily>?,
    @SerializedName("hourly")
    var hourly: List<Hourly>?,
    @SerializedName("lat")
    var lat: Double?,
    @SerializedName("lon")
    var lon: Double?,
    @SerializedName("timezone")
    var timezone: String?,
    @SerializedName("timezone_offset")
    var timezoneOffset: Double?
)