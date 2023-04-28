package com.mahmoudhamdyae.dailyforecast.data.remote

import com.mahmoudhamdyae.dailyforecast.data.remote.models.MainResponse
import com.mahmoudhamdyae.dailyforecast.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("data/2.5/forecast?appid=${Constants.API_KEY}")
    suspend fun getWeather(
        @Query("q") locationName: String
    ): MainResponse
}