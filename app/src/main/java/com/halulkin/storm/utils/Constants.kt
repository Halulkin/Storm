package com.halulkin.storm.utils

object ApiConfig {
    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val API_KEY = "appid"
    const val METRIC = "metric"
    val CNT: String = "10"
    var PLACE: String = "Prague"
}

object ApiEndPoint {
    const val GET_WEATHER_BY_LOCATION = "weather?"
    const val GET_FORECAST_BY_LOCATION = "forecast?"
}

var tempLatitude = 0.0
var tempLongitude= 0.0


