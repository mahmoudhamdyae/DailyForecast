package com.mahmoudhamdyae.dailyforecast.di

import com.mahmoudhamdyae.dailyforecast.data.local.WeatherDao
import com.mahmoudhamdyae.dailyforecast.data.remote.ApiService
import com.mahmoudhamdyae.dailyforecast.data.repository.WeatherRepositoryImpl
import com.mahmoudhamdyae.dailyforecast.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRepository(apiService: ApiService, dao: WeatherDao): WeatherRepository {
        return WeatherRepositoryImpl(apiService, dao)
    }
}