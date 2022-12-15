package com.axel.weatherapplibrary.model


import com.google.gson.annotations.SerializedName

data class Daily(
    @SerializedName("clouds")
    var clouds: Double?,
    @SerializedName("dew_point")
    var dewPoint: Double?,
    @SerializedName("dt")
    var dt: Double?,
    @SerializedName("feels_like")
    var feelsLike: FeelsLike?,
    @SerializedName("humidity")
    var humidity: Double?,
    @SerializedName("moon_phase")
    var moonPhase: Double?,
    @SerializedName("moonrise")
    var moonrise: Double?,
    @SerializedName("moonset")
    var moonset: Double?,
    @SerializedName("pop")
    var pop: Double?,
    @SerializedName("pressure")
    var pressure: Double?,
    @SerializedName("rain")
    var rain: Double?,
    @SerializedName("sunrise")
    var sunrise: Double?,
    @SerializedName("sunset")
    var sunset: Double?,
    @SerializedName("temp")
    var temp: Temp?,
    @SerializedName("uvi")
    var uvi: Double?,
    @SerializedName("weather")
    var weather: List<Weather>?,
    @SerializedName("wind_deg")
    var windDeg: Double?,
    @SerializedName("wind_gust")
    var windGust: Double?,
    @SerializedName("wind_speed")
    var windSpeed: Double?
)