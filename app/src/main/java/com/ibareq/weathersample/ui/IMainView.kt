package com.ibareq.weathersample.ui

import com.ibareq.weathersample.model.Status
import com.ibareq.weathersample.model.response.WeatherResponse
import io.reactivex.rxjava3.core.Observable

interface IMainView {

    fun onWeatherResult(response: Observable<Status<WeatherResponse>>)
    fun bindData(data: WeatherResponse)
    fun hideAllViews()
}