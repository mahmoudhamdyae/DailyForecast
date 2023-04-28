package com.mahmoudhamdyae.dailyforecast.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDao: WeatherDao {

    var weathers: MutableList<WeatherLocalModel> = mutableListOf()
    override suspend fun saveWeather(vararg weather: WeatherLocalModel) {
        weather.forEach {
            weathers.add(it)
        }
    }

    override fun getWeather(): Flow<List<WeatherLocalModel>> {
        return flow { emit(weathers) }
    }

    override fun delData() {
        weathers.clear()
    }
}