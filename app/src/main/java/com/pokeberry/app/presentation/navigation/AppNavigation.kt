package com.pokeberry.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pokeberry.app.network.ConnectivityObserver
import com.pokeberry.app.presentation.component.NoInternetBottomSheet
import com.pokeberry.app.presentation.screen.BerryDetailScreen
import com.pokeberry.app.presentation.screen.BerryScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val noInternetVersion by ConnectivityObserver.noInternetVersion.collectAsState()

    var dismissedVersion by rememberSaveable { mutableIntStateOf(0) }

    val showBottomSheet = noInternetVersion > dismissedVersion

    NavHost(
        navController = navController,
        startDestination = Screen.BerryList.route
    ) {

        composable(
            route = Screen.BerryList.route
        ) {

            BerryScreen(
                onBerryClick = { id ->

                    navController.navigate(
                        Screen.BerryDetail.createRoute(id)
                    )
                }
            )
        }

        composable(
            route = Screen.BerryDetail.route,

            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val id = backStackEntry
                .arguments
                ?.getInt("id") ?: 1

            BerryDetailScreen(
                berryId = id,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }

    if (showBottomSheet) {

        NoInternetBottomSheet(

            onRetry = {
                dismissedVersion = noInternetVersion
                ConnectivityObserver.signalRetry()
            }
        )
    }
}
