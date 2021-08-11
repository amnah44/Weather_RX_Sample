package com.ibareq.weathersample.presenter

import com.ibareq.weathersample.model.Status
import com.ibareq.weathersample.model.response.WeatherResponse
import io.reactivex.rxjava3.core.Observable

interface IPresenter {

    fun getWeatherForCity(cityName: String)


}