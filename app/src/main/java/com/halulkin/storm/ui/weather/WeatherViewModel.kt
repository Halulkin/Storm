package com.halulkin.storm.ui.weather

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.halulkin.storm.base.RxViewModel
import com.halulkin.storm.data.model.WeatherResponse
import com.halulkin.storm.data.repository.LocationRepository
import com.halulkin.storm.data.repository.WeatherRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class WeatherViewModel(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository
) : RxViewModel() {

    val location: LiveData<Location> get() = _location
    private val _location = locationRepository.newLocation

    private val _weatherResponse = MutableLiveData<WeatherResponse>()
    val weatherResponse: LiveData<WeatherResponse>
        get() = _weatherResponse

    fun startLocationUpdates() = locationRepository.startLocationUpdates()
    fun stopLocationUpdates() = locationRepository.stopLocationUpdates()
    fun getLastLocation() = locationRepository.getLastLocation()

    fun getWeatherByLocation(location: Location, units: String) {
        weatherRepository.getWeatherByLocation(location, units)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _weatherResponse.value = it
                },
                {
                    error.value = it.message
                }
            )
            .addTo(disposables)
    }

}