package com.axel.weatherapiapp.view.fragment

import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.axel.weatherapiapp.R
import com.axel.weatherapiapp.databinding.FragmentAddCityDialogBinding
import com.axel.weatherapplibrary.model.CityWeather
import com.axel.weatherapplibrary.viewmodel.WeatherViewModel
import java.util.*

class AddCityDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentAddCityDialogBinding
    private lateinit var weatherViewModel: WeatherViewModel
    private  var cities: List<CityWeather>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_add_city_dialog, container, false)

        binding.apply {
            addButton.isEnabled = false
            cancelButton.setOnClickListener { dismiss() }
            cityNameInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.isNotEmpty() == true) addButton.isEnabled = true
                }
                override fun afterTextChanged(s: Editable?) {}
            })
        }

        binding.addButton.setOnClickListener {

            val city: String = binding.cityNameInput.text.toString()
            val geocoder: Geocoder = Geocoder(requireContext(), Locale.getDefault())
            val addresses = geocoder.getFromLocationName(city, 2)
            val address = addresses?.get(0)

            address?.longitude?.let { it1 ->
                address.latitude.let { it2 ->
                    weatherViewModel.setWeather(it2, it1).observe(this) { weatherResponse ->
                        weatherViewModel.addCity(
                            requireContext(),
                            CityWeather(
                                0,
                                address.locality,
                                address.latitude,
                                address.longitude,
                                weatherResponse.current?.temp,
                                weatherResponse.current?.weather?.get(0)?.icon
                            )
                        )
                    }
                    Log.d(
                        "AXELCEDRIC",
                        "lati : ${address.latitude} \nlongi : ${address.longitude} \nCity : ${address.locality}"
                    )
                }
            }
            dismiss()
            val thread = Thread{
                cities = weatherViewModel.getAll(requireContext())
                activity?.runOnUiThread {
                    Log.d(
                        "AXELCEDRIC_ALL",
                        "latitude : ${cities?.get(0)?.lat} \nlongitude : ${cities?.get(0)?.lon} \nCityName : ${cities?.get(0)?.name}"
                    )
                }
            }.start()
            Toast.makeText(requireContext(), "${address?.locality} was saved successfully", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        weatherViewModel = ViewModelProvider(requireActivity())[WeatherViewModel::class.java]
    }
}