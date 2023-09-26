package com.sayanx.weatherview.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.sayanx.weatherview.model.room.Favourite
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    //Favourite:
    @Upsert
    suspend fun upsertFav(cityDetail: Favourite)

    @Delete
    suspend fun deleteFav(cityDetail: Favourite)

    @Query("SELECT * FROM fav_tbl")
    fun getAllFav(): Flow<List<Favourite>>


}