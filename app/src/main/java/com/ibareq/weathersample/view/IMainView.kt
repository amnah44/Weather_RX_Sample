package com.ibareq.weathersample.view

import com.ibareq.weathersample.model.Status
import com.ibareq.weathersample.model.response.WeatherResponse

interface IMainView {

    fun getWeatherForCity(cityName: String)
    fun onWeatherResult(response: Status<WeatherResponse>)
    fun bindData(data: WeatherResponse)
    fun hideAllViews()
}