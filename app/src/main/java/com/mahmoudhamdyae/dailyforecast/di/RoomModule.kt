package com.mahmoudhamdyae.dailyforecast.di

import android.content.Context
import androidx.room.Room
import com.mahmoudhamdyae.dailyforecast.data.local.WeatherDao
import com.mahmoudhamdyae.dailyforecast.data.local.WeatherDatabase
import com.mahmoudhamdyae.dailyforecast.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(database: WeatherDatabase): WeatherDao {
        return database.dao()
    }
}