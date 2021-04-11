package com.halulkin.storm.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Main(
    @Json(name = "humidity")
    var humidity: Int,

    @Json(name = "pressure")
    var pressure: Int,

    @Json(name = "temp_max")
    var tempMax: Float,

    @Json(name = "temp_min")
    var tempMin: Float,

    @Json(name = "feels_like")
    var feelsLike: Float,

    @Json(name = "temp")
    var temp: Float
)