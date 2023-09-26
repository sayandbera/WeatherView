package com.sayanx.weatherview.repository

import com.sayanx.weatherview.data.DataOrException
import com.sayanx.weatherview.data.WeatherDao
import com.sayanx.weatherview.model.remote.Weather
import com.sayanx.weatherview.model.room.Favourite
import com.sayanx.weatherview.network.WeatherApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherApi,
    private val dao: WeatherDao
) {

    // api:
    suspend fun getWeather(cityQuery: String, units: String): DataOrException<Weather, Boolean, Exception>
    {
        val response = try {
            api.getWeather(cityQuery = cityQuery, units = units)
        } catch (e: Exception) {
            return DataOrException(exception = e)
        }
        return DataOrException(data = response)
    }


    // database: Favourite
    suspend fun upsertFav(cityDetail: Favourite) { dao.upsertFav(cityDetail) }
    suspend fun deleteFav(cityDetail: Favourite) { dao.deleteFav(cityDetail) }
    fun getAllFav(): Flow<List<Favourite>> = dao.getAllFav()

}