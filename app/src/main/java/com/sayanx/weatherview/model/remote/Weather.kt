package com.sayanx.weatherview.model.remote

import com.sayanx.weatherview.model.remote.City
import com.sayanx.weatherview.model.remote.WeatherItem

data class Weather(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherItem>,
    val message: Double
)