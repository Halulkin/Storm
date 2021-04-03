package com.halulkin.storm.ui.weather

import android.location.Location
import androidx.lifecycle.LiveData
import com.halulkin.storm.base.RxViewModel
import com.halulkin.storm.data.repository.LocationRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class WeatherViewModel(
    private val locationRepository: LocationRepository
) : RxViewModel() {

    val location: LiveData<Location> get() = _location
    private val _location = locationRepository.newLocation

    fun startLocationUpdates() = locationRepository.startLocationUpdates()
    fun stopLocationUpdates() = locationRepository.stopLocationUpdates()
    fun getLastLocation() = locationRepository.getLastLocation()

}