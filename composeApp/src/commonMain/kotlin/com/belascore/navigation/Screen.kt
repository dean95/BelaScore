package com.belascore.navigation

private const val HOME_SCREEN_ROUTE = "Home"

sealed class Screen(
    val route: String,
) {
    data object Home : Screen(HOME_SCREEN_ROUTE)
}
