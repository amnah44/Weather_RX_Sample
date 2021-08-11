package com.ibareq.weathersample.model.response


import com.google.gson.annotations.SerializedName

data class LocationResponseItem(
    @SerializedName("latt_long")
    val lattLong: String,
    @SerializedName("location_type")
    val locationType: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("woeid")
    val cityId: Int
)