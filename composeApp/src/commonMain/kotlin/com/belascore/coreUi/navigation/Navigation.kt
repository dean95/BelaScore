package com.belascore.coreUi.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.belascore.gameHistory.ui.GameHistoryScreen
import com.belascore.home.ui.HomeScreen
import com.belascore.newGame.ui.NewGameScreen
import com.belascore.score.ui.ScoreScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf

@OptIn(KoinExperimentalAPI::class)
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
                    viewModel = koinViewModel(),
                    onNewGameClick = {
                        navController.navigate(NewGame)
                    },
                    onGameHistoryClick = {
                        navController.navigate(GameHistory)
                    },
                    onGameResumed = {
                        navController.navigate(Score(gameId = it))
                    }
                )
            }

            composable<NewGame> {
                NewGameScreen(
                    viewModel = koinViewModel(),
                    onStartGameClick = { navController.navigate(Score(gameId = it)) },
                    onBackClick = navController::navigateUp
                )
            }

            composable<Score> {
                val args = it.toRoute<Score>()
                ScoreScreen(
                    viewModel = koinViewModel(parameters = { parametersOf(args.gameId) })
                ) {
                    navController.popBackStack(
                        route = Home,
                        inclusive = false
                    )
                }
            }

            composable<GameHistory> {
                GameHistoryScreen(
                    viewModel = koinViewModel(),
                    onGameClick = {
                        // TODO: Navigate to score screen
                    },
                    onBackClick = navController::navigateUp
                )
            }
        }
    }
}
