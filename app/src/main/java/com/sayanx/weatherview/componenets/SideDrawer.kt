package com.sayanx.weatherview.componenets

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sayanx.weatherview.R
import com.sayanx.weatherview.navigation.Destinations
import com.sayanx.weatherview.navigation.NavigationGraph
import com.sayanx.weatherview.screens.favourite.FavouriteViewModel
import com.sayanx.weatherview.screens.home.HomeViewModel
import com.sayanx.weatherview.screens.settings.SettingsViewModel
import com.sayanx.weatherview.util.AppColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SideDrawer(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    favouriteViewModel: FavouriteViewModel,
    settingsViewModel: SettingsViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val screens = listOf(
        Destinations.HomeScreen,
        Destinations.SearchScreen,
        Destinations.FavouriteScreen,
        Destinations.SettingsScreen,
        Destinations.AboutScreen
        )

    ModalNavigationDrawer(
        gesturesEnabled = false,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = { scope.launch { drawerState.close() } }
                    )
                    .fillMaxHeight()
                    .width(230.dp)
                    .verticalScroll(state = ScrollState(0))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.red_sunny),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(10.dp)
                    )
                    Text(
                        modifier = Modifier.padding(end = 25.dp),
                        text = "WeatherView",
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowLeft,
                        contentDescription = "",
                        modifier = Modifier
                            .size(45.dp)
                            .padding(4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                screens.forEach { screen ->
                    NavigationDrawerItem(
                        modifier = Modifier.padding(start = 10.dp, top = 4.dp, bottom = 5.dp, end = 10.dp),
                        colors = NavigationDrawerItemDefaults.colors(selectedContainerColor = AppColor.lBlue),
                        icon = {
                            Icon(
                                modifier = Modifier.size(23.dp),
                                imageVector = screen.icon!!,
                                contentDescription = screen.route
                            )
                        },
                        label = { Text(text = screen.title!!, fontWeight = FontWeight(400)) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(route = screen.route) {
                                restoreState = true
                                launchSingleTop = true
                                popUpTo(screen.route) { saveState = false }
                            }
                        }
                    )
                }
            }
        },
        content = {
            when (currentRoute) {
                Destinations.HomeScreen.route ->
                    NavigationGraph(navController, scope, drawerState,  homeViewModel, favouriteViewModel, settingsViewModel)
                Destinations.SearchScreen.route ->
                    NavigationGraph(navController, scope, drawerState,  homeViewModel, favouriteViewModel, settingsViewModel)
                else ->
                    Scaffold(
                        topBar = {
                            DefaultTopBar(
                                title = screens.find { it.route == currentRoute }?.title ?: "",
                                scope = scope, drawerState = drawerState
                            )
                        }
                    ) { paddingValues ->
                        Column(
                            modifier = Modifier
                                .padding(paddingValues)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            NavigationGraph(navController, scope, drawerState,  homeViewModel, favouriteViewModel, settingsViewModel)
                        }
                    }
            }
        }
    )
}