package com.halulkin.storm.di

import com.halulkin.storm.ui.forecast.ForecastViewModel
import com.halulkin.storm.ui.weather.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { WeatherViewModel(get()) }
    viewModel { ForecastViewModel() }
}
