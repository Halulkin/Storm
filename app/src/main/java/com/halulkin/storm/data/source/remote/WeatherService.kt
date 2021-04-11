package com.halulkin.storm.data.source.remote

import com.halulkin.storm.data.model.WeatherResponse
import com.halulkin.storm.utils.ApiEndPoint
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET(ApiEndPoint.GET_WEATHER_BY_LOCATION)
    fun getWeatherByLocation(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("units") units: String
    ): Observable<WeatherResponse>

}
