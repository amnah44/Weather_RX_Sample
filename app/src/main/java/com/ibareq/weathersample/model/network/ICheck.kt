package com.ibareq.weathersample.model.network

import com.ibareq.weathersample.model.Status
import com.ibareq.weathersample.model.response.LocationResponse
import com.ibareq.weathersample.model.response.WeatherResponse
import okhttp3.Response

interface ICheck {

    fun getCheckResponseOfWeather(response: Response, weather: Class<WeatherResponse>): Status<WeatherResponse>
    fun getCheckResponseOfLocation(response: Response, weather: Class<LocationResponse>): Status<LocationResponse>

}