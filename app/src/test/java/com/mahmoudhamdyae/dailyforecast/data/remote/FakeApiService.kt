package com.mahmoudhamdyae.dailyforecast.data.remote

import com.mahmoudhamdyae.dailyforecast.data.remote.models.City
import com.mahmoudhamdyae.dailyforecast.data.remote.models.Coord
import com.mahmoudhamdyae.dailyforecast.data.remote.models.MainResponse

class FakeApiService: ApiService {

    override suspend fun getWeather(locationName: String): MainResponse {
        return MainResponse(
            city = City(
                coord = Coord(0.0, 0.0),
                country = locationName,
                id = 0,
                name = locationName,
                population = 0,
                sunrise = 0,
                sunset = 0,
                timezone = 0
            ),
            cnt = 0,
            cod = "",
            list = listOf(),
            message = 0
        )
    }
}