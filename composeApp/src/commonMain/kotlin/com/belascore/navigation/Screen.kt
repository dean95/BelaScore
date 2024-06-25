package com.belascore.navigation

private const val HOME_SCREEN_ROUTE = "Home"
private const val SCORE_SCREEN_ROUTE = "Score"

sealed class Screen(
    val route: String,
) {
    data object Home : Screen(HOME_SCREEN_ROUTE)

    data object Score : Screen(SCORE_SCREEN_ROUTE)
}
