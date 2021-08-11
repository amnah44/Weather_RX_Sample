package com.ibareq.weathersample.presenter

import com.ibareq.weathersample.model.Status
import com.ibareq.weathersample.model.network.Client
import com.ibareq.weathersample.model.response.WeatherResponse
import io.reactivex.rxjava3.core.Observable

class MainPresenter : IPresenter {

    private val client = Client
    private val locationInfo = LocationInfo(client)

    override fun getWeatherForCity(cityName: String): Observable<Status<WeatherResponse>> =
        locationInfo.getLocationInfo(cityName).flatMap {
            when (it) {
                is Status.Error -> {
                    Observable.create { emitter ->
                        emitter.onNext(it)
                        emitter.onComplete()
                    }
                }
                is Status.Loading -> {
                    Observable.create { emitter ->
                        emitter.onNext(it)
                        emitter.onComplete()
                    }
                }
                is Status.Success -> {
                    Observable.create { emitter ->
                        if (it.data.isEmpty()) {
                            emitter.onNext(Status.Error("city not found"))
                        } else {
                            emitter.onNext(client.getWeatherForCity(it.data[0].cityId)) //to make it easier we pick the first city and skip others
                        }
                        emitter.onComplete()
                    }
                }
            }
        }


}