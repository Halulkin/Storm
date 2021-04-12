package com.halulkin.storm.ui.forecast

import android.location.Location
import android.location.LocationManager
import com.example.storm.R
import com.example.storm.databinding.FragmentForecastBinding
import com.halulkin.storm.base.BaseFragment
import com.halulkin.storm.ui.adapters.ForecastAdapter
import com.halulkin.storm.utils.ApiConfig.METRIC
import com.halulkin.storm.utils.tempLatitude
import com.halulkin.storm.utils.tempLongitude
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForecastFragment : BaseFragment<FragmentForecastBinding>() {

    override val layoutResource: Int get() = R.layout.fragment_forecast
    override val viewModel by viewModel<ForecastViewModel>()
    private val forecastAdapter = ForecastAdapter()

    var location = Location(LocationManager.GPS_PROVIDER)

    override fun initData() {
        location.latitude = tempLatitude
        location.longitude = tempLongitude

        binding.apply {
            viewModel.getForecast(location, METRIC)
            lifecycleOwner = this@ForecastFragment
            forecastVM = viewModel
            recyclerForecast.adapter = forecastAdapter
        }
    }

}