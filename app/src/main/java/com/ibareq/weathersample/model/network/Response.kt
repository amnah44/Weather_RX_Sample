package com.ibareq.weathersample.model.network

import com.google.gson.Gson
import com.ibareq.weathersample.model.Status
import com.ibareq.weathersample.model.response.LocationResponse
import com.ibareq.weathersample.model.response.WeatherResponse
import okhttp3.Response

class Response(private val gson:Gson) : ICheck {

    override fun getCheckResponseOfWeather(
        response: Response,
        weather: Class<WeatherResponse>
    ): Status<WeatherResponse> {
        return if (response.isSuccessful) {
            val weatherResponse = gson.fromJson(response.body!!.string(), weather)
            Status.Success(weatherResponse)
        } else {
            Status.Error(response.message)
        }
    }

    override fun getCheckResponseOfLocation(
        response: Response,
        weather: Class<LocationResponse>
    ): Status<LocationResponse> {
        return if (response.isSuccessful) {
            val weatherResponse = gson.fromJson(response.body!!.string(), weather)
            Status.Success(weatherResponse)
        } else {
            Status.Error(response.message)
        }
    }

}