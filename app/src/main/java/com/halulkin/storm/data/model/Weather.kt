package com.halulkin.storm.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Weather(
    @Json(name = "icon")
    var icon: String?,

    @Json(name = "description")
    var description: String?,

    @Json(name = "main")
    var main: String?,

    @Json(name = "id")
    var id: Int
)