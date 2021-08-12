package com.ibareq.weathersample.model.repository

import com.ibareq.weathersample.model.Status
import com.ibareq.weathersample.model.response.LocationResponse
import com.ibareq.weathersample.model.response.WeatherResponse
import io.reactivex.rxjava3.core.Observable

interface IWeather {

    fun getWeatherForCity(cityName: String): Observable<Status<WeatherResponse>>
}