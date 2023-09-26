package com.sayanx.weatherview.screens.about

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.sayanx.weatherview.navigation.Destinations

@Composable
fun AboutScreen() {
    Text(text = Destinations.AboutScreen.title!!)
}