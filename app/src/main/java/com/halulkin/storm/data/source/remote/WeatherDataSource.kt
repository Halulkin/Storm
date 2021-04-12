package com.halulkin.storm.data.source.remote

import android.location.Location
import com.halulkin.storm.data.model.ForecastResponse
import com.halulkin.storm.data.model.WeatherResponse
import io.reactivex.rxjava3.core.Observable

interface WeatherDataSource {
    interface Remote {

        fun getWeatherByLocation(
            location: Location,
            units: String
        ): Observable<WeatherResponse>

        fun getForecastByLocation(
            location: Location,
            units: String
        ): Observable<ForecastResponse>

    }
}
