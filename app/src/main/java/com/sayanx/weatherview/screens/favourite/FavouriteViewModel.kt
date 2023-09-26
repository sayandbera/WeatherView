package com.sayanx.weatherview.screens.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayanx.weatherview.model.room.Favourite
import com.sayanx.weatherview.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repository: WeatherRepository) : ViewModel()
{

    private val _favList = MutableStateFlow<List<Favourite>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllFav().distinctUntilChanged()
                .collect {
                    if (it.isNotEmpty()) {
                        _favList.value = it
                    }
                }
        }
    }

    fun upsertFav(cityDetail: Favourite) = viewModelScope.launch { repository.upsertFav(cityDetail) }
    fun deleteFav(cityDetail: Favourite) = viewModelScope.launch { repository.deleteFav(cityDetail) }
}