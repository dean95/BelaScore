package com.belascore.coreUi.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.belascore.home.ui.HomeScreen
import com.belascore.newGame.ui.NewGameScreen
import com.belascore.score.ui.ScoreScreen

@Composable
fun Navigation() {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Home
        ) {
            composable<Home> {
                HomeScreen(
                    onNewGameClick = {
                        navController.navigate(NewGame)
                    },
                    onGameHistoryClick = {
                        /* no-op */
                    }
                )
            }

            composable<NewGame> {
                NewGameScreen {
                    navController.navigate(Score)
                }
            }

            composable<Score> {
                ScoreScreen(teams = listOf(), rounds = listOf())
            }
        }
    }
}
