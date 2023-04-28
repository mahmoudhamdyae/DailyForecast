package com.mahmoudhamdyae.dailyforecast.domain.usecase

import com.mahmoudhamdyae.dailyforecast.data.repository.FakeRepository
import com.mahmoudhamdyae.dailyforecast.util.Result
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetWeatherUseCaseTest {

    private lateinit var getWeather: GetWeatherUseCase
    private lateinit var fakeRepository: FakeRepository

    @Before
    fun setUp() {
        fakeRepository = FakeRepository()
        getWeather = GetWeatherUseCase(fakeRepository)
    }

    @Test
    fun getWeatherTest_returnSuccess() = runBlocking {
        val response = getWeather("Cairo")
        assert(response is Result.Success)
    }

    @Test
    fun getWeatherTest_returnError() = runBlocking {
        fakeRepository.setShouldReturnNetworkError(true)
        val response = getWeather("Cairo")
        assert(response is Result.Error)
    }
}