package com.sayanx.weatherview.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sayanx.weatherview.model.room.Favourite

@Database(entities = [Favourite::class], version = 4, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}