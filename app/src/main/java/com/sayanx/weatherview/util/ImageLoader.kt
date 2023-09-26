package com.sayanx.weatherview.util

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun WeatherStateImage(imgUrl: String) {
    AsyncImage(
        model = imgUrl,
        contentDescription = "Weather Icon Image",
        modifier = Modifier.size(65.dp)
    )
}