package com.mahmoudhamdyae.dailyforecast.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudhamdyae.dailyforecast.domain.usecase.GetWeatherUseCase
import com.mahmoudhamdyae.dailyforecast.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
): ViewModel() {

    var uiState by mutableStateOf(WeatherUiState())
        private set

    fun getWeather(locationName: String) {
        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true,
                error = null,
            )
            when (val response = getWeatherUseCase(locationName)) {
                is Result.Success -> {
                    response.data.collect {
                        uiState = uiState.copy(
                            weather = it,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                is Result.Error -> {
                    uiState = uiState.copy(
                        weather = listOf(),
                        isLoading = false,
                        error = response.message
                    )
                }
            }
        }
    }
}