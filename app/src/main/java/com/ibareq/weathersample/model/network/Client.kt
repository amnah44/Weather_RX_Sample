package com.ibareq.weathersample.model.network

import com.google.gson.Gson
import com.ibareq.weathersample.model.Status
import com.ibareq.weathersample.model.response.LocationResponse
import com.ibareq.weathersample.model.response.WeatherResponse
import okhttp3.OkHttpClient
import okhttp3.Request

object Client : IClient {
    private val okHttpClient = OkHttpClient()
    private const val baseUrl = "https://www.metaweather.com/api/"
    private val checkResponse = CheckResponse(Gson())

    override fun getWeatherForCity(cityId: Int): Status<WeatherResponse> {
        val request = Request.Builder().url("${baseUrl}location/$cityId/").build()
        val response = okHttpClient.newCall(request).execute()
        return checkResponse.getCheckResponseOfWeather(response, WeatherResponse::class.java)
    }

    override fun getLocationResponse(cityName: String): Status<LocationResponse> {
        val request = Request.Builder().url("${baseUrl}location/search/?query=$cityName").build()
        val response = okHttpClient.newCall(request).execute()
        return checkResponse.getCheckResponseOfLocation(response, LocationResponse::class.java)

    }
}