package com.sayanx.weatherview.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayanx.weatherview.datastore.UnitPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val unitPref: UnitPref): ViewModel() {

    val getUnitType = unitPref.getUnit().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ""
    )

    fun addUnit(unitType: String) {
        viewModelScope.launch(Dispatchers.IO) {
            unitPref.addUnit(unitType)
        }
    }

}
