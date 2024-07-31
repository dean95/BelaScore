package com.belascore.newGame.ui

data class NewGameUiState(
    val gameOptions: GameOptionsUiState = GameOptionsUiState(),
)

data class GameOptionsUiState(
    val playerCount: PlayerCount = PlayerCount.TWO,
    val teamCount: Int = if (playerCount == PlayerCount.THREE) 3 else 2,
    val winningScore: Int = playerCount.defaultScore
)
