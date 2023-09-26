package com.sayanx.weatherview.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayanx.weatherview.data.DataOrException
import com.sayanx.weatherview.model.remote.Weather
import com.sayanx.weatherview.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherRepository) : ViewModel() {

    // Splash Screen
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()
    init {
        viewModelScope.launch {
            delay(500)
            _isLoading.value = false
        }
    }

    // API
    suspend fun getWeather(city: String, units: String): DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather(cityQuery = city, units = units)
    }

}