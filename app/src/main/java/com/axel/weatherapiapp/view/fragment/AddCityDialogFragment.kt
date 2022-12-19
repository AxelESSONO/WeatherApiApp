package com.axel.weatherapiapp.view.fragment

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
                        // ccc
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })
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