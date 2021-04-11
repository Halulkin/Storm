package com.halulkin.storm.data.repository

import android.location.Location
import com.halulkin.storm.data.model.WeatherResponse
import com.halulkin.storm.data.source.remote.WeatherDataSource
import io.reactivex.rxjava3.core.Observable

class WeatherRepositoryImpl(
    private val remote: WeatherDataSource.Remote
) : WeatherRepository {

    override fun getWeatherByLocation(
        location: Location,
        units: String
    ): Observable<WeatherResponse> = remote.getWeatherByLocation(location, units)

}
