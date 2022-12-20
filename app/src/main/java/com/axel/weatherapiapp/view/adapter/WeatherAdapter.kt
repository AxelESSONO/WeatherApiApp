package com.axel.weatherapiapp.view.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.axel.weatherapiapp.R
import com.axel.weatherapiapp.databinding.DailyItemBinding
import com.axel.weatherapiapp.utils.FormatDate
import com.axel.weatherapiapp.utils.UnixTimeUtils
import com.axel.weatherapiapp.utils.fetchingIcons
import com.axel.weatherapplibrary.model.Hourly
import java.time.LocalDateTime


class WeatherAdapter(var hourlyDataList: List<Hourly>?) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(private val binding: DailyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(pHourly: Hourly?) = with(itemView) {
            val unixTimeUtils: UnixTimeUtils = UnixTimeUtils()

            val currentDate = LocalDateTime.now()
            val date = unixTimeUtils.format(
                pHourly?.dt?.toLong()?.let { unixTimeUtils.fromTimestamp(it) },
                FormatDate.HOUR_MIN_DAY_DAY_NUM_MONTH_YEAR
            )

            binding.apply {
                hourly = pHourly
                imageUrl = fetchingIcons(pHourly?.weather?.get(0)?.icon)
                dateByType = date
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding: DailyItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.daily_item, parent, false
        )
        return WeatherViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(hourlyDataList?.get(position))
    }

    override fun getItemCount(): Int = hourlyDataList?.size ?: 0
}