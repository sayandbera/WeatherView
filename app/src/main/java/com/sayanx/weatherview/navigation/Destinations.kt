package com.sayanx.weatherview.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.ui.graphics.vector.ImageVector

const val NavArgument_CITY = "city"

sealed class Destinations(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {

    // Passing the optional nav argument
    object HomeScreen : Destinations(
        route = "home_screen?city={$NavArgument_CITY}",
        title = "Home",
        icon = Icons.Rounded.Home
    ) {
        fun passCity(city: String): String {
            return "home_screen?city=$city" // for multiple: "home_screen?city=$city&$other=$otherValue"
        }
    }

    object SearchScreen : Destinations(
        route = "search_screen",
        title = "Search",
        icon = Icons.Rounded.Search
    )
    object FavouriteScreen : Destinations(
        route = "favourite_screen",
        title = "Favourites",
        icon = Icons.Rounded.Favorite
    )

    object SettingsScreen : Destinations(
        route = "setting_screen",
        title = "Settings",
        icon = Icons.Rounded.Settings
    )
    object AboutScreen : Destinations(
        route = "about_screen",
        title = "About",
        icon = Icons.Rounded.Info
    )

}
