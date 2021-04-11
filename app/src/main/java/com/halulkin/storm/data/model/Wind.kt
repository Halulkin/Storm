package com.halulkin.storm.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Wind(
    @Json(name = "deg")
    var deg: Int,

    @Json(name = "speed")
    var speed: Float
)