package com.sayanx.weatherview.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sayanx.weatherview.screens.about.AboutScreen
import com.sayanx.weatherview.screens.home.HomeScreen
import com.sayanx.weatherview.screens.search.SearchScreen
import com.sayanx.weatherview.screens.settings.SettingsScreen
import com.sayanx.weatherview.screens.favourite.FavouriteScreen
import com.sayanx.weatherview.screens.favourite.FavouriteViewModel
import com.sayanx.weatherview.screens.home.HomeViewModel
import com.sayanx.weatherview.screens.settings.SettingsViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    scope: CoroutineScope,
    drawerState: DrawerState,
    homeViewModel: HomeViewModel,
    favouriteViewModel: FavouriteViewModel,
    settingsViewModel: SettingsViewModel
) {
    val city = rememberSaveable { mutableStateOf("") }
    NavHost(
        navController = navController,
        startDestination = Destinations.HomeScreen.route
    ) {

        composable(
            route = Destinations.HomeScreen.route,
            arguments = listOf(
                navArgument(NavArgument_CITY) {
                    type = NavType.StringType
                    defaultValue = city.value
                }
            )
        ) {
            city.value = it.arguments?.getString(NavArgument_CITY, "").toString()
            HomeScreen(
                navController = navController,
                homeViewModel = homeViewModel,
                favouriteViewModel = favouriteViewModel,
                settingsViewModel = settingsViewModel,
                scope = scope,
                drawerState = drawerState,
                city = city.value
            )
        }

        composable(route = Destinations.SearchScreen.route) {
            SearchScreen(navController = navController)
        }
        composable(route = Destinations.FavouriteScreen.route) {
            FavouriteScreen(navController, favouriteViewModel)
        }
        composable(route = Destinations.SettingsScreen.route) {
            SettingsScreen(settingsViewModel)
        }
        composable(route = Destinations.AboutScreen.route) {
            AboutScreen()
        }
    }
}
