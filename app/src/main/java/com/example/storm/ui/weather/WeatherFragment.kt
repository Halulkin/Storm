package com.example.storm.ui.weather

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Resources
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.storm.R
import com.example.storm.databinding.FragmentWeatherBinding
import com.example.storm.utils.hasPermission
import com.example.storm.utils.isGpsEnabled
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
    private lateinit var gpsSwitchStateReceiver: BroadcastReceiver
    var gpsEnabled = false

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        val root: View = binding.root


        getGpsLocation()

        weatherViewModel.location.observe(viewLifecycleOwner, {
            Log.e(
                "TAG",
                "Update from far: " + it?.latitude.toString() + ", " + it?.longitude.toString()
            )
            binding.tvCityName.text = it?.latitude.toString() + ", " + it?.longitude.toString()
        })

        return root
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume: ")
        gpsEnabled = requireActivity().isGpsEnabled()
        weatherViewModel.startLocationUpdates()
        registerGpsSwitchStateReceiver()
    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG, "onPause:")
        weatherViewModel.stopLocationUpdates()
        unregisterGpsSwitchStateReceiver()
    }

    fun setupViews() {
        val activity = requireActivity()
        if (activity.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            gpsEnabled
        ) {

            binding.tvCityName.text = binding.tvCityName.text.toString().apply {
                this + "Hello"
            }
        } else {
            binding.tvCityName.text = "wewewewewe"
        }
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
//                        showWeather()
                        weatherViewModel.startLocationUpdates()
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

    private fun registerGpsSwitchStateReceiver() {
        gpsSwitchStateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent?) {
                gpsEnabled = context.isGpsEnabled()
                setupViews()
            }
        }
        val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        filter.addAction(Intent.ACTION_PROVIDER_CHANGED)
        requireActivity().registerReceiver(gpsSwitchStateReceiver, filter)
    }

    private fun unregisterGpsSwitchStateReceiver() {
        requireActivity().unregisterReceiver(gpsSwitchStateReceiver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "WeatherFragment"
    }
}