package com.halulkin.storm.ui.forecast

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.halulkin.storm.base.RxViewModel
import com.halulkin.storm.data.model.ForecastResponse
import com.halulkin.storm.data.repository.WeatherRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class ForecastViewModel(
    private val weatherRepository: WeatherRepository
) : RxViewModel() {

    private val _forecast = MutableLiveData<ForecastResponse>()
    val forecast: LiveData<ForecastResponse>
        get() = _forecast

    fun getForecast(location: Location, units: String) {
        weatherRepository.getForecastByLocation(location, units)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _forecast.value = it
                },
                { error.value = it.message }
            )
            .addTo(disposables)
    }
}