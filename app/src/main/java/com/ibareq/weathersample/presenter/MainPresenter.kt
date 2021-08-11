package com.ibareq.weathersample.presenter

import com.ibareq.weathersample.model.repository.WeatherRepository
import com.ibareq.weathersample.view.IMainView

class MainPresenter(private val view: IMainView) : IPresenter {

    //handle weather from api
    override fun getWeatherForCity(cityName: String) {
        view.onWeatherResult(WeatherRepository.getWeatherForCity(cityName))
    }


}