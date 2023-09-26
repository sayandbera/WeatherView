package com.sayanx.weatherview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sayanx.weatherview.componenets.SideDrawer
import com.sayanx.weatherview.screens.favourite.FavouriteViewModel
import com.sayanx.weatherview.screens.home.HomeViewModel
import com.sayanx.weatherview.screens.settings.SettingsViewModel
import com.sayanx.weatherview.ui.theme.WeatherViewTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val favouriteViewModel: FavouriteViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                homeViewModel.isLoading.value
            }
        } //splash screen
        setContent {
            WeatherViewTheme(darkTheme = false) {
                val navController: NavHostController = rememberNavController()
                SideDrawer(
                    navController = navController,
                    homeViewModel = homeViewModel,
                    favouriteViewModel = favouriteViewModel,
                    settingsViewModel = settingsViewModel
                )
            }
        }
    }
}