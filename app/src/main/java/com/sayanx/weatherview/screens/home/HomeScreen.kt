package com.sayanx.weatherview.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sayanx.weatherview.componenets.weather_display.BottomWeatherDisplay
import com.sayanx.weatherview.componenets.weather_display.TopWeatherDisplay
import com.sayanx.weatherview.data.DataOrException
import com.sayanx.weatherview.model.remote.Weather
import com.sayanx.weatherview.navigation.Destinations
import com.sayanx.weatherview.screens.favourite.FavouriteViewModel
import com.sayanx.weatherview.screens.settings.SettingsViewModel
import com.sayanx.weatherview.util.AppColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    city: String,
    navController: NavController,
    homeViewModel: HomeViewModel,
    favouriteViewModel: FavouriteViewModel,
    settingsViewModel: SettingsViewModel,
    scope: CoroutineScope,
    drawerState: DrawerState
) {
    val unitFromDataStore = settingsViewModel.getUnitType.collectAsState().value
    val unit = remember { mutableStateOf(unitFromDataStore.ifBlank { "metric" }) }
    val isImperial = remember { mutableStateOf(unit.value=="imperial") }

    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)) {
        value = homeViewModel.getWeather(
            units = unit.value,
            city = city.ifBlank {
                if (favouriteViewModel.favList.value.isNotEmpty()) favouriteViewModel.favList.value[0].city else "London"
            },
        )
    }.value

    Scaffold(
        topBar = {
            Surface(
                shape = RoundedCornerShape(30.dp),
                color = AppColor.elBlue,
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp)
                    .heightIn(max = 45.dp)
            ) {
                TopAppBar(
                    modifier = Modifier.clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = { navController.navigate(Destinations.SearchScreen.route) }
                    ),
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = Color.Transparent,
                        actionIconContentColor = Color(0xFF007BFF)
                    ),
                    navigationIcon = {
                        IconButton(
                            modifier = Modifier.fillMaxHeight(),
                            onClick = {
                                scope.launch { drawerState.open() }
                            }
                        ) {
                            Icon(
                                tint = Color.Black,
                                imageVector = Icons.Rounded.Menu,
                                contentDescription = "Drawer Icon"
                            )
                        }
                    },
                    title = {
                        Text(
                            text = "${city.ifBlank {
                                if (favouriteViewModel.favList.value.isNotEmpty()) favouriteViewModel.favList.value[0].city else "London"
                            }} ${if (weatherData.data?.city?.country.isNullOrEmpty()) "" else ", ${weatherData.data?.city?.country}"}",
                            color = Color.DarkGray,
                            modifier = Modifier.padding(start = 10.dp),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                letterSpacing = 0.2.sp
                            )
                        )
                    },
                    actions = {
                        Row {
                            Icon(
                                tint = Color.Black,
                                contentDescription = "Search Icon",
                                modifier = Modifier.padding(end = 15.dp),
                                imageVector = Icons.Rounded.Search
                            )
                        }
                    }
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            when (weatherData.loading) {
                true -> {
                    CircularProgressIndicator()
                }
                else -> {
                    weatherData.data?.let {
                        TopWeatherDisplay(
                            weather = it,
                            isImperial = isImperial.value,
                            favouriteViewModel = favouriteViewModel)
                        BottomWeatherDisplay(weather = it)
                    }
                }
            }
        }
    }
}