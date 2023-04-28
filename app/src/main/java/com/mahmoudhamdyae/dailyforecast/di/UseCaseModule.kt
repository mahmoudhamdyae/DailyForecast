package com.mahmoudhamdyae.dailyforecast.di

import com.mahmoudhamdyae.dailyforecast.domain.repository.WeatherRepository
import com.mahmoudhamdyae.dailyforecast.domain.usecase.GetWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideUseCase(repository: WeatherRepository): GetWeatherUseCase {
        return GetWeatherUseCase(repository)
    }
}