package com.ibareq.weathersample.presenter

import com.ibareq.weathersample.model.Status
import com.ibareq.weathersample.model.network.Client
import com.ibareq.weathersample.model.response.LocationResponse
import io.reactivex.rxjava3.core.Observable

open class LocationInfo(private val client: Client){

    open fun getLocationInfo(cityName: String): Observable<Status<LocationResponse>> =
        Observable.create { emitter ->
            emitter.onNext(Status.Loading)
            emitter.onNext(client.getLocationResponse(cityName))
            emitter.onComplete()
        }

}