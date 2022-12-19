package com.axel.weatherapiapp.view.fragment

import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.axel.weatherapiapp.R
import com.axel.weatherapiapp.databinding.FragmentAddCityDialogBinding
import java.util.*

class AddCityDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentAddCityDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_add_city_dialog, container, false)

        binding.apply {
            addButton.isEnabled = false
            cancelButton.setOnClickListener { dismiss() }
            cityNameInput.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.isNotEmpty() == true){
                        addButton.isEnabled = true
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

        binding.addButton.setOnClickListener {

            val city: String = binding.cityNameInput.text.toString()
            val geocoder: Geocoder = Geocoder(requireContext(), Locale.getDefault())
            val addresses = geocoder.getFromLocationName(city, 2)

            val address = addresses?.get(0)
            dismiss()

            Toast.makeText(
                requireContext(),
                "latitude : ${address?.latitude} \nlongitude : ${address?.longitude} \nCity name : ${address?.locality}",
                Toast.LENGTH_SHORT
            ).show()

        }

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddCityDialogFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}