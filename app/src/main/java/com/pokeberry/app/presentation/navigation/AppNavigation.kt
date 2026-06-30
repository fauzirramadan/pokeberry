package com.pokeberry.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pokeberry.app.presentation.screen.BerryDetailScreen
import com.pokeberry.app.presentation.screen.BerryScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

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
}