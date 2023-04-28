package com.mahmoudhamdyae.dailyforecast.ui

import com.mahmoudhamdyae.dailyforecast.domain.model.WeatherModel

data class WeatherUiState(
    val weather: List<WeatherModel> = listOf(),
    val isLoading: Boolean = false,
    val error: String? = null,
)