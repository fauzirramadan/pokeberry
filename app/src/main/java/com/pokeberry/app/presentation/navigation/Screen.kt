package com.pokeberry.app.presentation.navigation

sealed class Screen(
    val route: String
) {

    data object BerryList : Screen("berry_list")

    data object BerryDetail : Screen("berry_detail/{id}") {

        fun createRoute(id: Int): String {
            return "berry_detail/$id"
        }
    }
}