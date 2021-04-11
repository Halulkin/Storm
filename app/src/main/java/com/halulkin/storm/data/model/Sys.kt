package com.halulkin.storm.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sys(
    @Json(name = "sunset")
    var sunset: Long,

    @Json(name = "sunrise")
    var sunrise: Long,

    @Json(name = "country")
    var country: String?,

    @Json(name = "id")
    var id: Int,

    @Json(name = "type")
    var type: Int
)