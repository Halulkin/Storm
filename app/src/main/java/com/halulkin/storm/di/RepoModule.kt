package com.halulkin.storm.di

import com.halulkin.storm.data.repository.LocationRepository
import com.halulkin.storm.data.repository.WeatherRepository
import com.halulkin.storm.data.repository.WeatherRepositoryImpl
import com.halulkin.storm.data.source.local.LocationManager
import com.halulkin.storm.data.source.remote.WeatherDataSource
import com.halulkin.storm.data.source.remote.WeatherRemoteDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val locationRepoModule = module {
    single { LocationManager(androidContext()) }
    single { LocationRepository(get()) }
}

val weatherRepoModule = module {
    single<WeatherDataSource.Remote> { WeatherRemoteDataSource(get()) }
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
}