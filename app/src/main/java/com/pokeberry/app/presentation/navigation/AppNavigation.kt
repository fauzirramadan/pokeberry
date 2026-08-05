package com.pokeberry.app.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pokeberry.app.network.ConnectivityObserver
import com.pokeberry.app.presentation.component.NoInternetBottomSheet
import com.pokeberry.app.presentation.screen.BerryDetailScreen
import com.pokeberry.app.presentation.screen.BerryScreen
import com.pokeberry.app.presentation.screen.SettingsScreen
import com.pokeberry.app.presentation.screen.TaskScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val noInternetVersion by ConnectivityObserver.noInternetVersion.collectAsState()

    var dismissedVersion by rememberSaveable { mutableIntStateOf(0) }

    val showBottomSheet = noInternetVersion > dismissedVersion

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val tabRoutes = listOf(
        Screen.BerryList.route, Screen.Task.route, Screen.Settings.route
    )

    Scaffold(
        bottomBar = {
            if (currentRoute in tabRoutes) {
                NavigationBar {
                    val items = listOf(
                        Triple(Screen.BerryList.route, Icons.Default.Home, "Berries"),
                        Triple(Screen.Task.route, Icons.Default.Task, "Tasks"),
                        Triple(Screen.Settings.route, Icons.Default.Settings, "Settings")
                    )

                    items.forEach { (route, icon, label) ->
                        val selected = currentRoute == route
                        NavigationBarItem(
                            icon = { Icon(icon, contentDescription = label) },
                            label = { Text(label) },
                            selected = selected,
                            onClick = {
                                if (!selected) {
                                    navController.navigate(route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            })
                    }
                }
            }
        }) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Screen.BerryList.route,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(
                route = Screen.BerryList.route
            ) {

                BerryScreen(
                    onBerryClick = { id ->

                        navController.navigate(
                            Screen.BerryDetail.createRoute(id)
                        )
                        
                    })
            }

            composable(
                route = Screen.Task.route
            ) {
                TaskScreen()
            }

            composable(
                route = Screen.Settings.route
            ) {
                SettingsScreen()
            }

            composable(
                route = Screen.BerryDetail.route,

                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    })
            ) { backStackEntry ->

                val id = backStackEntry.arguments?.getInt("id") ?: 1

                BerryDetailScreen(
                    berryId = id, onBackClick = {
                        navController.popBackStack()
                    })
            }
        }
    }

    if (showBottomSheet) {

        NoInternetBottomSheet(

            onRetry = {
                dismissedVersion = noInternetVersion
                ConnectivityObserver.signalRetry()
            })
    }
}
