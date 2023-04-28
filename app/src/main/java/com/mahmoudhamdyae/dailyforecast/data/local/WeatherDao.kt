package com.mahmoudhamdyae.dailyforecast.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeather(vararg weather: WeatherLocalModel)

    @Query("SELECT * FROM weather_table")
    fun getWeather(): Flow<List<WeatherLocalModel>>

    @Query("DELETE FROM weather_table")
    fun delData()
}