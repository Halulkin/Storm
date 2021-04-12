package com.halulkin.storm.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Forecast(
    @Json(name = "dt_txt")
    var dtTxt: String? = null,

    @Json(name = "wind")
    var wind: Wind? = null,

    @Json(name = "weather")
    var weather: List<Weather>? = null,

    @Json(name = "main")
    var main: Main? = null,

    @Json(name = "dt")
    var dt: Int = 0

) : GeneralEntity {

    override fun areItemsTheSame(newItem: GeneralEntity): Boolean =
        newItem is Forecast && this.weather == newItem.weather

    override fun areContentsTheSame(newItem: GeneralEntity): Boolean =
        newItem is Forecast && this == newItem
}
