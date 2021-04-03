package com.halulkin.storm.ui.weather

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.storm.R
import com.example.storm.databinding.FragmentWeatherBinding
import com.halulkin.storm.utils.hasPermission
import com.halulkin.storm.utils.isGpsEnabled
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherFragment : Fragment() {

    private val weatherViewModel by viewModel<WeatherViewModel>()

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)

        showWeather()

        weatherViewModel.location.observe(viewLifecycleOwner, {
            binding.tvCityName.text = it?.latitude.toString() + ", " + it?.longitude.toString()
            binding.swiperefresh.isRefreshing = false
        })

        binding.swiperefresh.setOnRefreshListener {
            showWeather()
        }

        return binding.root
    }

    private fun showWeather() {
        val activity = requireActivity()
        val gpsEnabled = activity.isGpsEnabled()

        Log.e("TAG", "showWeather - gpsEnabled: $gpsEnabled")

        if (activity.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            activity.isGpsEnabled()
        ) {
            weatherViewModel.getLastLocation()
        } else {
            getGpsLocation()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume:")
        weatherViewModel.startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG, "onPause:")
        weatherViewModel.stopLocationUpdates()
    }

    private fun getGpsLocation() {
        Log.e("TAG", "getGpsLocation")

        Dexter.withContext(requireActivity())
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    val locationReq = LocationRequest.create().apply {
                        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    }
                    val builder = LocationSettingsRequest.Builder().apply {
                        addLocationRequest(locationReq)
                    }
                    val result = LocationServices.getSettingsClient(requireActivity())
                        .checkLocationSettings(
                            builder.build()
                        )
                    result.addOnSuccessListener {
                        showWeather()
                        Toast.makeText(context, "Ready to show weather!", Toast.LENGTH_SHORT).show()
                        Log.e("TAG", "addOnSuccessListener")
                    }
                    // when permission.ACCESS_FINE_LOCATION granted, but location is disabled
                    // show location dialog
                    result.addOnFailureListener { exception ->
                        if (exception is ResolvableApiException) {
                            Log.e("TAG", "addOnFailureListener")
                            exception.startResolutionForResult(requireActivity(), 321)
                        }
                    }
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    if (response.isPermanentlyDenied) {
                        // show snackBar allowing app setting navigation
                        Snackbar
                            .make(
                                binding.layout,
                                getString(R.string.change_location_permission),
                                Snackbar.LENGTH_LONG
                            )
                            .setAction(getString(R.string.ok)) {
                                val intent =
                                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri: Uri = Uri.fromParts(
                                    "package",
                                    requireActivity().packageName,
                                    null
                                )
                                intent.data = uri
                                startActivity(intent)
                            }
                            .show()
                    } else {
                        // show snackBar with rationale
                        Snackbar
                            .make(
                                binding.layout,
                                getString(R.string.permission_rejection_text),
                                Snackbar.LENGTH_LONG
                            )
                            .show()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "WeatherFragment"
    }
}