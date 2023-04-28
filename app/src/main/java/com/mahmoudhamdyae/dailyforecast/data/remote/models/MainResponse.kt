package com.mahmoudhamdyae.dailyforecast.data.remote.models

import com.mahmoudhamdyae.dailyforecast.data.local.WeatherLocalModel

data class MainResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<ListObject>,
    val message: Int
)

fun MainResponse.asLocalModel(): Array<WeatherLocalModel> {
    return list.map {
        WeatherLocalModel(
            cityName = this.city.name,
            temp = it.main.temp - 273.15,
            weather = it.weather.first().main,
            windSpeed = it.wind.speed,
            date = it.dt_txt.split(" ").first(),
            time = it.dt_txt.split(" ")[1].dropLast(3),
        )
    }.toTypedArray()
}