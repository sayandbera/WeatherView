package com.sayanx.weatherview.screens.settings

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sayanx.weatherview.util.AppColor

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel) {

    val measurementUnit = listOf("imperial", "metric")
    val unitFromDataStore = settingsViewModel.getUnitType.collectAsState().value
    val selectedUnit = remember { mutableStateOf(unitFromDataStore.ifEmpty { measurementUnit[0] }) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(20.dp),
                    color = AppColor.lBlue
                )
                .height(140.dp),
            verticalArrangement = Arrangement.Top
        ) {

            Text(
                text = "Change Units of Measurement",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(10.dp)
            )
// Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
            Column {
                measurementUnit.forEach { unit ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                                onClick = {
                                    selectedUnit.value = unit
                                }
                            )
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (unit == selectedUnit.value),
                            onClick = {
                                selectedUnit.value = unit
                            },
                            colors = RadioButtonDefaults.colors(selectedColor = Color.Blue)
                        )
                        Text(
                            text = unit,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 7.dp)
                        )
                    }
                }
            }
        }
        Button(
            enabled = unitFromDataStore != selectedUnit.value,
            modifier = Modifier.padding(top = 15.dp),
            onClick = {
                settingsViewModel.addUnit(selectedUnit.value)
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDB3500))
        ) {
            Text(text = "Save", color = Color.White)
        }
    }
}