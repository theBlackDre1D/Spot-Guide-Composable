package com.g3.spot_guide.screens.main

import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.g3.spot_guide.R
import com.g3.spot_guide.screens.crew.CrewScreenUI
import com.g3.spot_guide.screens.map.MapScreenUI
import com.g3.spot_guide.screens.profile.ProfileScreenUI
import com.g3.spot_guide.screens.settings.SettingsScreenUI

sealed class HomeBottomNavigationScreens(val route: String, @StringRes val labelResId: Int) {
    object MapScreen : HomeBottomNavigationScreens("map", R.string.screen_label__map)
    object ProfileScreen : HomeBottomNavigationScreens("profile", R.string.screen_label__profile)
    object CrewScreen : HomeBottomNavigationScreens("crew", R.string.screen_label__crew)
    object SettingsScreen : HomeBottomNavigationScreens("settings", R.string.screen_label__settings)
}

@Composable
fun MainActivityUI() {

    val navController = rememberNavController()

    val homeBottomNavigationScreens = listOf(
        HomeBottomNavigationScreens.MapScreen,
        HomeBottomNavigationScreens.ProfileScreen,
        HomeBottomNavigationScreens.CrewScreen,
        HomeBottomNavigationScreens.SettingsScreen
    )

    Scaffold(
        bottomBar = { BottomBar(navController, homeBottomNavigationScreens) }
    ) {
        NavHost(navController, startDestination = HomeBottomNavigationScreens.MapScreen.route) {
            composable(HomeBottomNavigationScreens.MapScreen.route) { MapScreenUI() }
            composable(HomeBottomNavigationScreens.ProfileScreen.route) { ProfileScreenUI() }
            composable(HomeBottomNavigationScreens.CrewScreen.route) { CrewScreenUI() }
            composable(HomeBottomNavigationScreens.SettingsScreen.route) { SettingsScreenUI() }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController, homeBottomNavigationScreens: List<HomeBottomNavigationScreens>) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
        homeBottomNavigationScreens.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                label = { Text(stringResource(screen.labelResId)) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo = navController.graph.startDestination
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}