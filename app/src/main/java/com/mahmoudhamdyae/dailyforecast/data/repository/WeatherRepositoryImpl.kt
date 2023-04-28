package com.mahmoudhamdyae.dailyforecast.data.repository

import android.util.Log
import com.mahmoudhamdyae.dailyforecast.data.local.WeatherDao
import com.mahmoudhamdyae.dailyforecast.data.local.asDomainModel
import com.mahmoudhamdyae.dailyforecast.data.remote.ApiService
import com.mahmoudhamdyae.dailyforecast.data.remote.models.asLocalModel
import com.mahmoudhamdyae.dailyforecast.domain.model.WeatherModel
import com.mahmoudhamdyae.dailyforecast.domain.repository.WeatherRepository
import com.mahmoudhamdyae.dailyforecast.util.wrapEspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(
    private val apiService: ApiService,
    private val dao: WeatherDao
): WeatherRepository {

    override suspend fun getWeather(locationName: String): Flow<List<WeatherModel>> {
        wrapEspressoIdlingResource {
            var weather: Flow<List<WeatherModel>> = flow { }
            var isAccurateData = true

            withContext(Dispatchers.IO) {
                try {
                    val remoteWeather = apiService.getWeather(locationName)
                    dao.delData()
                    dao.saveWeather(*remoteWeather.asLocalModel())
                } catch (e: Exception) {
                    isAccurateData = false
                    Log.e("Repository", e.message.toString())
                } finally {
                    weather = dao.getWeather().map {
                        it.asDomainModel(isAccurateData)
                    }
                }
            }

            return weather
        }
    }
}