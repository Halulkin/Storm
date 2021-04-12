package com.halulkin.storm.data.source.remote

import android.location.Location
import com.halulkin.storm.data.model.ForecastResponse
import com.halulkin.storm.data.model.WeatherResponse
import io.reactivex.rxjava3.core.Observable

class WeatherRemoteDataSource(private val weatherService: WeatherService) :
    WeatherDataSource.Remote {

    override fun getWeatherByLocation(
        location: Location,
        units: String
    ): Observable<WeatherResponse> =
        weatherService.getWeatherByLocation(
            location.latitude.toString(),
            location.longitude.toString(),
            units
        )

    override fun getForecastByLocation(
        location: Location,
        units: String
    ): Observable<ForecastResponse> =
    weatherService.getForecastByLocation(
    location.latitude.toString(),
    location.longitude.toString(),
    units
    )
}
