package com.ibareq.weathersample.model.repository

import com.ibareq.weathersample.model.Status
import com.ibareq.weathersample.model.network.Client
import com.ibareq.weathersample.model.response.LocationResponse
import com.ibareq.weathersample.model.response.WeatherResponse
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter

object WeatherRepository: IWeather {

    private val locationInfo = LocationInfo(Client)
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
                        //to check if city found in Api or not
                       isItemFoundInApi(it,emitter)
                        emitter.onComplete()
                    }
                }
            }
        }

    private fun isItemFoundInApi(
        status: Status.Success<LocationResponse>,
        emitter: @NonNull ObservableEmitter<Status<WeatherResponse>>
    ) {
        if (status.data.isEmpty()) {
            emitter.onNext(Status.Error("city not found"))
        } else {
            emitter.onNext(Client.getWeatherForCity(status.data[0].cityId)) //to make it easier we pick the first city and skip others
        }
    }
}