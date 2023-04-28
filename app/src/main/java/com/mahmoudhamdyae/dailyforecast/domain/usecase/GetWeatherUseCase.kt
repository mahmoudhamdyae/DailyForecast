package com.mahmoudhamdyae.dailyforecast.domain.usecase

import com.mahmoudhamdyae.dailyforecast.domain.model.WeatherModel
import com.mahmoudhamdyae.dailyforecast.domain.repository.WeatherRepository
import com.mahmoudhamdyae.dailyforecast.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(locationName: String): Result<Flow<List<WeatherModel>>> {
        return try {
            Result.Success(repository.getWeather(locationName))
        } catch (e: Exception) {
            Result.Error(e.message ?: "Something wrong happened. Please try again.")
        }
    }
}