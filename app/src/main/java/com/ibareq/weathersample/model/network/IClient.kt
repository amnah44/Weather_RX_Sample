package com.ibareq.weathersample.model.network

import com.ibareq.weathersample.model.Status
import com.ibareq.weathersample.model.response.LocationResponse
import com.ibareq.weathersample.model.response.WeatherResponse

interface IClient {

    fun getWeatherForCity(cityId: Int): Status<WeatherResponse>
    fun getLocationResponse(cityName: String): Status<LocationResponse>

}