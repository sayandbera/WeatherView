package com.sayanx.weatherview.componenets.weather_display

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sayanx.weatherview.R
import com.sayanx.weatherview.model.remote.Weather
import com.sayanx.weatherview.model.remote.WeatherItem
import com.sayanx.weatherview.model.room.Favourite
import com.sayanx.weatherview.screens.favourite.FavouriteViewModel
import com.sayanx.weatherview.util.Utils
import com.sayanx.weatherview.util.WeatherStateImage

@Composable
fun TopWeatherDisplay(
    weather: Weather,
    favouriteViewModel: FavouriteViewModel,
    isImperial: Boolean
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircleDisplay(weather = weather, favouriteViewModel = favouriteViewModel, isImperial = isImperial)
        HumidityWindPressure(weatherItem = weather.list[0], isImperial = isImperial)
        SunsetSunrise(weatherItem = weather.list[0])
        Divider(thickness = 0.6.dp)
    }
}

@Composable
fun CircleDisplay(weather: Weather, favouriteViewModel: FavouriteViewModel, isImperial: Boolean) {
    val context = LocalContext.current
    val favList = favouriteViewModel.favList.collectAsState().value
    val isFavourite = remember { mutableStateOf(
        favList.contains(Favourite(city = weather.city.name, country = weather.city.country))
    ) }
    val imgUrl = "https://openweathermap.org/img/wn/${weather.list[0].weather[0].icon}.png"
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "")
        Text( // Date
            text = (Utils.formateDate(weather.list[0].dt)),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 36.dp)
        )
        Icon(
            imageVector = if (isFavourite.value) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
            contentDescription = "Favourite Icon",
            tint = Color(0xFF0487EB),
            modifier = Modifier
                .scale(0.95f)
                .padding(top = 5.dp, end = 24.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    onClick = {
                        isFavourite.value = !isFavourite.value
                        if (isFavourite.value) {
                            favouriteViewModel.upsertFav(
                                Favourite(
                                    city = weather.city.name,
                                    country = weather.city.country
                                )
                            )
                            Toast.makeText(context, "Added to Favourite List", Toast.LENGTH_SHORT).show()
                        } else {
                            favouriteViewModel.deleteFav(
                                Favourite(
                                    city = weather.city.name,
                                    country = weather.city.country
                                )
                            )
                            Toast.makeText(context, "Removed from Favourite List", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
        )
    }

    Surface(
        modifier = Modifier
            .padding(4.dp)
            .size(190.dp),
        shape = CircleShape
    ) {
        Column(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(listOf(Color(0xFF55B1FA), Color(0xFFFFFFFF)))
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WeatherStateImage(imgUrl = imgUrl)
            Text( // Temperature
                text = "${Utils.formateDecimals(weather.list[0].temp.day)}${if (isImperial)"°F" else "°C"}",
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text( // weather condition
                text = weather.list[0].weather[0].main,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Composable
fun HumidityWindPressure(weatherItem: WeatherItem, isImperial: Boolean) {
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 5.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.barometer),
                contentDescription = "Pressure Icon",
                modifier = Modifier.size(16.dp)
            )
            Text(
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 4.dp),
                text = "${weatherItem.pressure} psi",
                fontWeight = FontWeight.SemiBold
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "Wind Icon",
                modifier = Modifier.size(17.dp)
            )
            Text(
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 3.dp),
                text = "${Utils.formateDecimals(weatherItem.speed)} ${if (isImperial) "mph" else "m/s"}",
                fontWeight = FontWeight.SemiBold
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "Humidity",
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = "${weatherItem.humidity}%",
                modifier = Modifier.padding(horizontal = 3.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun SunsetSunrise(weatherItem: WeatherItem) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 2.dp, bottom = 9.dp, start = 25.dp, end = 19.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "sunrise",
                modifier = Modifier
                    .size(30.dp)
                    .padding(bottom = 2.dp)
            )
            Text(
                text = Utils.formateDateTime(weatherItem.sunrise),
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 1.dp),
                fontWeight = FontWeight.SemiBold
            )
        }

        Text(text = "▼", modifier = Modifier.padding(top = 40.dp), color = Color.Green)

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.sunset),
                contentDescription = "sunset",
                modifier = Modifier.size(30.dp)
            )
            Text(
                Utils.formateDateTime(weatherItem.sunset),
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 3.dp),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}