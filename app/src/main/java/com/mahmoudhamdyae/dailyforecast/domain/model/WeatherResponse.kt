package com.mahmoudhamdyae.dailyforecast.domain.model

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

data class WeatherModel(
    val cityName: String,
    val temp: Double,
    val weather: String,
    val windSpeed: Double,
    val date: String,
    val time: String,
    val isAccurateData: Boolean
)

@SuppressLint("SimpleDateFormat")
fun String.toDate(): String {
    val today = SimpleDateFormat("yyyy-MM-dd").format(Date()) == this
    val tomorrow = SimpleDateFormat("yyyy-MM-dd")
        .format(Date(Date().time + (1000 * 60 * 60 * 24))) == this
    return if (today) "Today" else if (tomorrow) "Tomorrow" else this
}
