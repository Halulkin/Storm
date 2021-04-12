package com.halulkin.storm.ui.weather

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.storm.R
import com.example.storm.databinding.FragmentWeatherBinding
import com.github.matteobattilana.weather.PrecipType
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import com.halulkin.storm.base.BaseFragment
import com.halulkin.storm.utils.ApiConfig.METRIC
import com.halulkin.storm.utils.hasPermission
import com.halulkin.storm.utils.isGpsEnabled
import com.halulkin.storm.utils.tempLatitude
import com.halulkin.storm.utils.tempLongitude
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherFragment : BaseFragment<FragmentWeatherBinding>() {

    override val layoutResource: Int get() = R.layout.fragment_weather
    override val viewModel by viewModel<WeatherViewModel>()

    override fun initData() {
        observeLocationChanges()
        observeWeatherResponse()

        binding.apply {
            lifecycleOwner = this@WeatherFragment
            weatherVM = viewModel
            swipeRefresh.setOnRefreshListener {
                getLocation()
            }
        }
        getLocation()
    }

    private fun observeWeatherResponse() {
        viewModel.weatherResponse.observe(viewLifecycleOwner, {
            binding.swipeRefresh.isRefreshing = false
            setupRandomWeatherBackground()
        })
    }

    private fun observeLocationChanges() {
        viewModel.location.observe(viewLifecycleOwner, {
            if (it != null) {
                viewModel.stopLocationUpdates()
                viewModel.getWeatherByLocation(it, METRIC)
                tempLatitude = it.latitude
                tempLongitude = it.longitude
            }
        })
    }

    private fun getLocation() {
        binding.swipeRefresh.isRefreshing = true
        val activity = requireActivity()
        if (activity.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            activity.isGpsEnabled()
        ) {
            viewModel.getLastLocation()
        } else {
            getGpsLocation()
        }
    }

    private val resolutionForResult =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { activityResult ->
            when (activityResult.resultCode) {
                Activity.RESULT_OK -> viewModel.startLocationUpdates()
                Activity.RESULT_CANCELED -> binding.swipeRefresh.isRefreshing = false
            }
        }

    private fun getGpsLocation() {
        Dexter.withContext(requireActivity())
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    showEnableLocationSetting()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    binding.swipeRefresh.isRefreshing = false
                    if (response.isPermanentlyDenied) {
                        // show snackBar allowing app setting navigation
                        Snackbar
                            .make(
                                binding.swipeRefresh,
                                getString(R.string.change_location_permission),
                                Snackbar.LENGTH_LONG
                            )
                            .setAction(getString(R.string.ok)) {
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri: Uri = Uri.fromParts(
                                    "package",
                                    requireActivity().packageName,
                                    null
                                )
                                intent.data = uri
                                startActivity(intent)
                            }.show()
                    } else {
                        // show snackBar with rationale
                        Snackbar.make(
                            binding.swipeRefresh,
                            getString(R.string.permission_rejection_text),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    request: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            }).check()
    }

    fun showEnableLocationSetting() {
        activity?.let {
            val locationRequest = LocationRequest.create()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
            val task = LocationServices.getSettingsClient(it)
                .checkLocationSettings(builder.build())
            task.addOnSuccessListener {
                getLocation()
            }
            task.addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    try {
                        val intentSenderRequest =
                            IntentSenderRequest.Builder(exception.resolution).build()
                        resolutionForResult.launch(intentSenderRequest)
                    } catch (throwable: Throwable) {
                        // Ignore the error.
                    }
                }
            }
        }
    }

    private fun setupRandomWeatherBackground() {
        when ((1..2).random()) {
            1 -> {
                binding.weatherView.setWeatherData(PrecipType.RAIN)
            }
            2 -> {
                binding.weatherView.setWeatherData(PrecipType.SNOW)
            }
        }
    }
}