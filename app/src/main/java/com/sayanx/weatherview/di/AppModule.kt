package com.sayanx.weatherview.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.sayanx.weatherview.data.WeatherDao
import com.sayanx.weatherview.data.WeatherDatabase
import com.sayanx.weatherview.datastore.UnitPref
import com.sayanx.weatherview.network.WeatherApi
import com.sayanx.weatherview.util.ApiConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }


    @Singleton
    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao {
        return weatherDatabase.weatherDao()
    }

    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context): WeatherDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = WeatherDatabase::class.java,
            name = "weather_database"
        ).fallbackToDestructiveMigration().build()
    }


    // Preference DataStore
    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = {context.preferencesDataStoreFile("unit_type")}
        )
    }

    @Singleton
    @Provides
    fun provideUnitPref(dataStore: DataStore<Preferences>): UnitPref = UnitPref(dataStore)

}