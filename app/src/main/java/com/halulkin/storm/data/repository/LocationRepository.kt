package com.halulkin.storm.data.repository

import com.halulkin.storm.data.source.local.LocationManager

class LocationRepository(private val locationManager: LocationManager) {

    val newLocation = locationManager.newLocation

    fun startLocationUpdates() = locationManager.startLocationUpdates()

    fun stopLocationUpdates() = locationManager.stopLocationUpdates()

    fun getLastLocation() = locationManager.getLastLocation()
}