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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class HourlyAdapter(var hourlyDataList: List<Hourly>?) :
    RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>() {

    class HourlyViewHolder(private val binding: DailyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(pHourly: Hourly?) = with(itemView) {
            val unixTimeUtils: UnixTimeUtils = UnixTimeUtils()

            val currentDate = LocalDateTime.now()

           /* val date = LocalDateTime.parse(unixTimeUtils.format(
                pHourly?.dt?.toLong()?.let { unixTimeUtils.fromTimestamp(it) },
                FormatDate.HOUR_MIN_DAY_DAY_NUM_MONTH_YEAR),
                DateTimeFormatter.ofPattern("dd:MM:yyyy")
            )*/


            val date = unixTimeUtils.format(
                pHourly?.dt?.toLong()?.let { unixTimeUtils.fromTimestamp(it) },
                FormatDate.HOUR_MIN_DAY_DAY_NUM_MONTH_YEAR
            )

            binding.apply {
                hourly = pHourly
                imageUrl = fetchingIcons(pHourly?.weather?.get(0)?.icon)
               /* dateByType = unixTimeUtils.format(
                    pHourly?.dt?.toLong()?.let { unixTimeUtils.fromTimestamp(it) },
                    FormatDate.HOUR_MIN_DAY_DAY_NUM_MONTH_YEAR
                )*/

                dateByType = date

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val binding: DailyItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.daily_item, parent, false
        )
        return HourlyViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        holder.bind(hourlyDataList?.get(position))
    }

    override fun getItemCount(): Int = hourlyDataList?.size ?: 0
}