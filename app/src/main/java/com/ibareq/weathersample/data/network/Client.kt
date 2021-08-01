package com.ibareq.weathersample.data.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ibareq.weathersample.data.Status
import com.ibareq.weathersample.data.response.LocationResponse
import com.ibareq.weathersample.data.response.WeatherResponse
import okhttp3.OkHttpClient
import okhttp3.Request

object Client {
    private val okHttpClient = OkHttpClient()
    private val baseUrl = "https://www.metaweather.com/api/"
    private val gson = GsonBuilder().create()

    fun getWeatherForCity(cityId: Int): Status<WeatherResponse>{
        val request = Request.Builder().url("${baseUrl}location/$cityId/").build()
        val response = okHttpClient.newCall(request).execute()
        return if (response.isSuccessful){
            val weatherResponse = gson.fromJson(response.body!!.string(), WeatherResponse::class.java)
            Status.Success(weatherResponse)
        } else {
            Status.Error(response.message)
        }
    }

    fun getLocationResponse(cityName: String): Status<LocationResponse>{
        val request = Request.Builder().url("${baseUrl}location/search/?query=$cityName").build()
        val response = okHttpClient.newCall(request).execute()
        return if (response.isSuccessful){
            val locationResponse = gson.fromJson(response.body!!.string(), LocationResponse::class.java)
            Status.Success(locationResponse)
        } else {
            Status.Error(response.message)
        }
    }
}