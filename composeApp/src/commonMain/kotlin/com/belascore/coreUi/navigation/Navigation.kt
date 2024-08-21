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
                        navController.navigate(route = Score(gameId = it))
                    }
                )
            }

            composable<NewGame> {
                NewGameScreen(
                    viewModel = koinViewModel(),
                    onStartGameClick = {
                        navController.navigate(
                            route = Score(
                                gameId = it,
                                source = NewGame.toString(),
                                showGameResultDialog = true
                            )
                        )
                    },
                    onBackClick = navController::navigateUp
                )
            }

            composable<Score> {
                val args = it.toRoute<Score>()
                ScoreScreen(
                    viewModel = koinViewModel(parameters = { parametersOf(args.gameId) }),
                    showGameResultDialog = args.showGameResultDialog
                ) {
                    when (args.source) {
                        NewGame.toString() -> navController.popBackStack(
                            route = Home,
                            inclusive = false
                        )

                        else -> navController.navigateUp()
                    }
                }
            }

            composable<GameHistory> {
                GameHistoryScreen(
                    viewModel = koinViewModel(),
                    onGameClick = {
                        navController.navigate(
                            route = Score(
                                gameId = it.id,
                                source = GameHistory.toString()
                            )
                        )
                    },
                    onBackClick = navController::navigateUp
                )
            }
        }
    }
}
