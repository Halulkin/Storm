package com.halulkin.storm.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class ForecastResponse(
    @Json(name = "list")
    var list: List<Forecast>? = null
)