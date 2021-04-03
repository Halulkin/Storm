package com.halulkin.storm.ui.weather

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halulkin.storm.data.source.local.LocationRepository

class WeatherViewModel(
    private val repository: LocationRepository
) : ViewModel() {

    val location: LiveData<Location> get() = _location
    private val _location = repository.newLocation

    fun startLocationUpdates() = repository.startLocationUpdates()
    fun stopLocationUpdates() = repository.stopLocationUpdates()
    fun getLastLocation() = repository.getLastLocation()


}