package com.mahmoudhamdyae.dailyforecast.domain.repository

import com.mahmoudhamdyae.dailyforecast.domain.model.WeatherModel
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getWeather(locationName: String): Flow<List<WeatherModel>>
}