package com.sayanx.weatherview.datastore

import kotlinx.coroutines.flow.Flow

interface UnitInterface {
    fun getUnit(): Flow<String>
    suspend fun addUnit(unitType: String)
}