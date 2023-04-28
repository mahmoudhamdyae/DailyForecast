package com.mahmoudhamdyae.dailyforecast.data.repository

import com.mahmoudhamdyae.dailyforecast.MainCoroutineRule
import com.mahmoudhamdyae.dailyforecast.data.local.FakeDao
import com.mahmoudhamdyae.dailyforecast.data.remote.FakeApiService
import com.mahmoudhamdyae.dailyforecast.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch

class WeatherRepositoryTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var repository: WeatherRepository

    private lateinit var remoteDataSource: FakeApiService
    private lateinit var localDataSource: FakeDao

    @Before
    fun createRepository() {
        remoteDataSource = FakeApiService()
        localDataSource = FakeDao()
        // Get a reference to the class under test
        repository = WeatherRepositoryImpl(
            remoteDataSource, localDataSource
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getWeatherData() = mainCoroutineRule.runBlockingTest {
        val latch = CountDownLatch(1)
        async(Dispatchers.IO) {
            repository.getWeather("Cairo").collect {
                assert(it.first().cityName == "Cairo")
                latch.countDown()
            }
        }
    }
}