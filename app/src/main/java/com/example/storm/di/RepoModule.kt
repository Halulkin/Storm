package com.example.storm.di

import com.example.storm.data.source.local.LocationManager
import com.example.storm.data.source.local.LocationRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val locationRepoModule = module {
    single { LocationManager(androidContext()) }
    single { LocationRepository(get()) }
}