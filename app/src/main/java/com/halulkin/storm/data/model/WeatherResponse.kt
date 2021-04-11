package com.halulkin.storm.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.*

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    @Json(name = "cod")
    var cod: Int,

    @Json(name = "name")
    var name: String?,

    @Json(name = "id")
    var id: Int,

    @Json(name = "timezone")
    var timezone: Int,

    @Json(name = "sys")
    var sys: Sys? = null,

    @Json(name = "dt")
    var dt: Long?,

    @Json(name = "clouds")
    var clouds: Clouds?,

    @Json(name = "wind")
    var wind: Wind?,

    @Json(name = "visibility")
    var visibility: Int,

    @Json(name = "main")
    var main: Main?,

    @Json(name = "base")
    var base: String?,

    @Json(name = "weather")
    var weather: List<Weather>?,

    @Json(name = "coord")
    var coord: Coord?
)