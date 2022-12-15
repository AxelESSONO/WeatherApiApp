package com.axel.weatherapplibrary.model


import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("description")
    var description: String?,
    @SerializedName("icon")
    var icon: String?,
    @SerializedName("id")
    var id: Double?,
    @SerializedName("main")
    var main: String?
)