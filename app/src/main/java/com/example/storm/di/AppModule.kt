package com.example.storm.di

import com.example.storm.ui.forecast.ForecastViewModel
import com.example.storm.ui.weather.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { WeatherViewModel(get()) }
    viewModel { ForecastViewModel() }
}
