package com.example.storm.ui.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.storm.databinding.FragmentWeatherBinding
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
        val root: View = binding.root

        weatherViewModel.getLastLocation()


        val textView: TextView = binding.textDashboard
//        weatherViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        weatherViewModel.location.observe(viewLifecycleOwner, {
            Log.e(
                "TAG",
                "Update from far: " + it?.latitude.toString() + ", " + it?.longitude.toString()
            )
            textView.text = it?.latitude.toString() + ", " + it?.longitude.toString()
        })

//        weatherViewModel.location.observe(viewLifecycleOwner, Observer {
//            textView.text = it.toString()
//        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}