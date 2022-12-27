package com.axel.weatherapiapp.view.fragment

import android.content.DialogInterface
import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.axel.weatherapiapp.R
import com.axel.weatherapiapp.databinding.FragmentAddCityDialogBinding
import com.axel.weatherapplibrary.model.CityWeather
import com.axel.weatherapplibrary.viewmodel.CityWeatherViewModel
import com.axel.weatherapplibrary.viewmodel.WeatherViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.*

class AddCityDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentAddCityDialogBinding
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var cityWeatherViewModel: CityWeatherViewModel

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

            val city: String = binding.cityNameInput.text.toString().trim()
            val geocoder: Geocoder = Geocoder(requireContext(), Locale.getDefault())
            val addresses = geocoder.getFromLocationName(city, 2)
            val address = addresses?.get(0)

            var messageResponse = ""


            if (address != null) {
                cityWeatherViewModel.saveCity(
                    CityWeather(
                        0,
                        address.locality,
                        address.latitude,
                        address.longitude,
                        weatherViewModel.setWeather(address.latitude, address.longitude).value?.current?.temp,
                        weatherViewModel.setWeather(address.latitude, address.longitude).value?.current?.weather?.get(0)?.icon
                    )
                )
                Log.d("NDZOGOMVE", "City : ${address.locality} lati : ${address.latitude} long : ${address.longitude}")
                messageResponse = "${address.locality} was saved successfully"
            }else{
                messageResponse = "Failed to save this city"
            }

            dismiss()
            Snackbar.make(binding.root,messageResponse, Snackbar.LENGTH_SHORT).show()
        }
        return binding.root
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        weatherViewModel = ViewModelProvider(requireActivity())[WeatherViewModel::class.java]
        cityWeatherViewModel = ViewModelProvider(requireActivity())[CityWeatherViewModel::class.java]
    }
}