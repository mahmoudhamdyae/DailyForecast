package com.mahmoudhamdyae.dailyforecast.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mahmoudhamdyae.dailyforecast.domain.model.WeatherModel

@Entity(tableName = "weather_table")
data class WeatherLocalModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val cityName: String,
    val temp: Double,
    val weather: String,
    val windSpeed: Double,
    val date: String,
    val time: String,
)

fun List<WeatherLocalModel>.asDomainModel(isAccurateData: Boolean): List<WeatherModel> {
    return map {
        WeatherModel(
            cityName = it.cityName,
            temp = String.format("%.1f", it.temp).toDouble(),
            weather = it.weather,
            windSpeed = it.windSpeed,
            date = it.date,
            time = it.time,
            isAccurateData = isAccurateData
        )
    }
}