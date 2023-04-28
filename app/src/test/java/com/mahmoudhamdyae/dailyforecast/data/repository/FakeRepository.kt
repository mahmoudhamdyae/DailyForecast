package com.mahmoudhamdyae.dailyforecast.data.repository

import com.mahmoudhamdyae.dailyforecast.domain.model.WeatherModel
import com.mahmoudhamdyae.dailyforecast.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository : WeatherRepository {

    private val weather1 = WeatherModel(
        cityName = "Cairo",
        temp = 37.0,
        weather = "",
        windSpeed = 0.0,
        date = "",
        time = "",
        isAccurateData = true
    )
    private val weather2 = weather1.copy(cityName = "Mansoura", temp = 38.0)
    private val weather = listOf(weather1, weather2)

    private var shouldReturnError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun getWeather(locationName: String): Flow<List<WeatherModel>> {
        return if (!shouldReturnError) flow { emit(weather) } else throw Exception("Test Exception")
    }
}