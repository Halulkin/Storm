package com.halulkin.storm.ui.weather

import android.location.Location
import androidx.lifecycle.LiveData
import com.halulkin.storm.base.RxViewModel
import com.halulkin.storm.data.source.local.LocationRepository

class WeatherViewModel(
    private val repository: LocationRepository
) : RxViewModel() {

    val location: LiveData<Location> get() = _location
    private val _location = repository.newLocation

    fun startLocationUpdates() = repository.startLocationUpdates()
    fun stopLocationUpdates() = repository.stopLocationUpdates()
    fun getLastLocation() = repository.getLastLocation()


}