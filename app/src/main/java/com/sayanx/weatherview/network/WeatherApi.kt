package com.sayanx.weatherview.network

import com.sayanx.weatherview.model.remote.Weather
import com.sayanx.weatherview.util.ApiConstants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET("/data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") cityQuery: String,
        @Query("appid") appid: String = ApiConstants.API_KEY,
        @Query("units") units: String
    ): Weather
}