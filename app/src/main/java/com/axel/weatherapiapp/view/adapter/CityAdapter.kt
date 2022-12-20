package com.axel.weatherapiapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.axel.weatherapiapp.R
import com.axel.weatherapiapp.databinding.CityItemBinding

import com.axel.weatherapiapp.utils.fetchingIcons
import com.axel.weatherapplibrary.model.CityWeather

class CityAdapter(private val cities: List<CityWeather>) :
    RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    class CityViewHolder(private val binding: CityItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bingView(pCityWeather: CityWeather) {
            binding.apply {
                weatherCity = pCityWeather
                imageUrl = fetchingIcons(pCityWeather.icon)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder =
        CityViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.city_item, parent, false
            )
        )


    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bingView(cities[position])
    }

    override fun getItemCount(): Int = cities.size
}