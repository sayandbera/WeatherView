package com.sayanx.weatherview.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sayanx.weatherview.navigation.Destinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    val newCity = rememberSaveable { mutableStateOf("") }
    val valid = remember(newCity.value){ newCity.value.trim().isNotEmpty() || newCity.value.trim().isNotBlank() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,

    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray
            ),
            placeholder = {
                Text(text = " Search place..", color = Color.Gray)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            value = newCity.value,
            onValueChange = { newValue -> newCity.value = newValue },
            leadingIcon = {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "Back Icon"
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (valid) {
                            navController.navigate(Destinations.HomeScreen.passCity(newCity.value.trim()))
                            newCity.value = ""
                        }
                        else {
                            navController.navigate(Destinations.HomeScreen.route)
                        }
                    }
                ) {
                    Icon(
                        tint = if (newCity.value.isEmpty()) Color.Transparent else Color(0xFF003A81),
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Search"
                    )
                }
            },
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (valid) {
                        navController.navigate(Destinations.HomeScreen.passCity(newCity.value.trim()))
                        newCity.value = ""
                    }
                    else {
                        navController.navigate(Destinations.HomeScreen.route)
                    }
                }
            )
        )
        Divider(thickness = 0.6.dp, color = Color.LightGray)
    }
}