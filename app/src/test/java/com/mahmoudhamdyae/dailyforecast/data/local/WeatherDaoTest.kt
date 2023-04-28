package com.mahmoudhamdyae.dailyforecast.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
class WeatherDaoTest {

    private lateinit var database: WeatherDatabase
    private lateinit var dao: WeatherDao

    private val weather1 = WeatherLocalModel(
        cityName = "Cairo",
        temp = 37.0,
        weather = "",
        windSpeed = 0.0,
        date = "",
        time = "",
    )
    private val weather2 = weather1.copy(cityName = "Mansoura", temp = 38.0)

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.dao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    @Throws(IOException::class)
    fun saveWeather_ReadInList() = runBlocking {
        dao.saveWeather(weather1)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            dao.getWeather().collect {
                assert(it.contains(weather1))
                latch.countDown()
            }
        }
        job.cancelAndJoin()
    }

    @Test
    @Throws(IOException::class)
    fun removeWeathers_ReadList() = runBlocking {
        dao.saveWeather(weather1, weather2)
        dao.delData()

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            dao.getWeather().collect {
                assert(!it.contains(weather1))
                latch.countDown()
            }
        }
        job.cancelAndJoin()
    }
}