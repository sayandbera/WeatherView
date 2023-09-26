package com.sayanx.weatherview.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UnitPref(private val dataStore: DataStore<Preferences>): UnitInterface {
    override fun getUnit(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[UNIT_KEY] ?: ""
        }
    }

    override suspend fun addUnit(unitType: String) {
        dataStore.edit { preferences ->
            preferences[UNIT_KEY] = unitType
        }
    }

    companion object {
        private val UNIT_KEY = stringPreferencesKey("unit_key")
    }
}
