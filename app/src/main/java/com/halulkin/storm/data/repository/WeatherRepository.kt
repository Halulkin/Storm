package com.halulkin.storm.data.repository

import android.location.Location
import com.halulkin.storm.data.model.ForecastResponse
import com.halulkin.storm.data.model.WeatherResponse
import io.reactivex.rxjava3.core.Observable

interface WeatherRepository {

    fun getWeatherByLocation(
        location: Location,
        units: String
    ): Observable<WeatherResponse>

    fun getForecastByLocation(
        location: Location,
        units: String
    ): Observable<ForecastResponse>

}
