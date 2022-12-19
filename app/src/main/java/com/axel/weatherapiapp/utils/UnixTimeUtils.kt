package com.axel.weatherapiapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class UnixTimeUtils() {

    fun dateFrom(timestamp: Long): Date = Date(timestamp)


    fun calendarFrom(timestamp: Long): Calendar {
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return calendar
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fromNanos(timestamp: Long): Instant? {
        val seconds = timestamp / 1_000_000_000
        val nanos = timestamp % 1_000_000_000
        return Instant.ofEpochSecond(seconds, nanos)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fromTimestamp(timestamp: Long): Instant? = Instant.ofEpochMilli(millis(timestamp))


    @RequiresApi(Build.VERSION_CODES.O)
    fun format(instant: Instant?, formatDate: FormatDate): String? {
        val time: LocalDateTime = localTimeUtc(instant)

        return when(formatDate){
            FormatDate.DAY_DAY_NUM_MONTH_YEAR -> time.format(DateTimeFormatter.ofPattern("dd:MM:yyyy"))
            FormatDate.HOUR_MIN -> time.format(DateTimeFormatter.ofPattern("HH:mm"))
            FormatDate.HOUR_MIN_DAY_DAY_NUM_MONTH_YEAR -> time.format(DateTimeFormatter.ofPattern("HH:mm - dd:MM:yyyy"))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun localTimeUtc(instant: Instant?): LocalDateTime {
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
    }

    private fun millis(timestamp: Long): Long {
        if (timestamp >= 1E16 || timestamp <= -1E16) {
            return timestamp / 1000000
        }
        if (timestamp >= 1E14 || timestamp <= -1E14) {
            return timestamp / 1000
        }
        return if (timestamp >= 1E11 || timestamp <= -3E10) {
            timestamp
        } else timestamp * 1000
    }

}