package com.axel.weatherapiapp.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.axel.weatherapiapp.R
import com.axel.weatherapiapp.databinding.CityItemBinding

import com.axel.weatherapiapp.utils.fetchingIcons
import com.axel.weatherapplibrary.model.CityWeather
import com.axel.weatherapplibrary.viewmodel.WeatherViewModel

class CityAdapter(
    private val context : AppCompatActivity,
    private var weatherViewModel: WeatherViewModel,
    private var cities: List<CityWeather>,
    var listener : (CityWeather) ->  Unit
) :
    RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    inner class CityViewHolder(private val binding: CityItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NotifyDataSetChanged")
        fun bingView(pCityWeather: CityWeather, listener: (CityWeather) -> Unit) {
            binding.apply {
                weatherCity = pCityWeather
                imageUrl = fetchingIcons(pCityWeather.icon)

                deleteCity.setOnClickListener {
                    /*weatherViewModel.deleteCity(context, pCityWeather)
                    cities.drop(adapterPosition)
                    notifyDataSetChanged()
                    Snackbar.make(binding.root,"Device'location was permitted", Snackbar.LENGTH_SHORT).show()*/
                    listener(pCityWeather)
                }
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
        holder.bingView(cities[position], listener)
    }

    override fun getItemCount(): Int = cities.size
}