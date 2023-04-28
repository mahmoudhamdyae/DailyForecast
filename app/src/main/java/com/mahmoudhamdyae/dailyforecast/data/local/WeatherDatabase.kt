package com.mahmoudhamdyae.dailyforecast.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WeatherLocalModel::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun dao(): WeatherDao
}