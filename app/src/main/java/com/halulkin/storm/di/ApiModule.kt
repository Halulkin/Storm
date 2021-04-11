package com.halulkin.storm.di

import com.halulkin.storm.data.source.remote.WeatherService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single { get<Retrofit>().create(WeatherService::class.java) }
}
