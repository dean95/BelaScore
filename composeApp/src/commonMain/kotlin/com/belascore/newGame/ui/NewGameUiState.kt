package com.belascore.newGame.ui

data class NewGameUiState(
    val gameOptions: GameOptionsUiState = GameOptionsUiState(),
)

data class GameOptionsUiState(
    val playerCount: PlayerCount = PlayerCount.TWO,
    val teamCount: Int = 2,
    val winningScore: Int = playerCount.defaultScore
)
