package com.belascore.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.belascore.home.ui.HomeScreen
import com.belascore.newGame.ui.NewGameScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onNewGameClick = {
                        navController.navigate(Screen.NewGame.route)
                    },
                    onGameHistoryClick = {},
                )
            }

            composable(Screen.NewGame.route) {
                NewGameScreen()
            }
        }
    }
}
