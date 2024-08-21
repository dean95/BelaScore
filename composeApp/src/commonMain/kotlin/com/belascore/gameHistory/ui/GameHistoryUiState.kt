package com.belascore.gameHistory.ui

data class GameHistoryUiState(
    val games: List<GameSummaryUiState> = emptyList()
)

data class GameSummaryUiState(
    val id: Long,
    val teams: List<TeamUiState>
)

data class TeamUiState(
    val id: Long,
    val name: String,
    val score: Int
)
