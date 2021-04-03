package com.halulkin.storm.di

import com.halulkin.storm.data.source.local.LocationManager
import com.halulkin.storm.data.repository.LocationRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val locationRepoModule = module {
    single { LocationManager(androidContext()) }
    single { LocationRepository(get()) }
}