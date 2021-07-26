package com.ibareq.weathersample.data.repository

import com.ibareq.weathersample.data.Status
import com.ibareq.weathersample.data.network.Client
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow


object WeatherRepository {
    @FlowPreview
    fun getWeatherForCity(cityName: String) = getLocationInfo(cityName).flatMapConcat {
        when (it) {
            is Status.Error -> {
                flow {
                    emit(it)
                }
            }
            is Status.Loading -> {
                flow {
                    emit(it)
                }
            }
            is Status.Success -> {
                flow {
                    if (it.data.isEmpty()) {
                        emit(Status.Error("city not found"))
                    } else {
                        emit(Client.getWeatherForCity(it.data[0].cityId)) //to make it easier we pick the first city and skip others
                    }
                }
            }
        }


    }

    private fun getLocationInfo(cityName: String) = flow {
        emit(Status.Loading)
        emit(Client.getLocationResponse(cityName))
    }


}