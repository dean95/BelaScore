package com.belascore.score.ui

data class ScoreUiState(
    val teams: List<TeamUiState> = emptyList(),
    val rounds: List<RoundItemUiState> = emptyList()
)

data class RoundItemUiState(
    val roundNumber: Int,
    val scores: Map<String, Int>,
)

data class TeamUiState(
    val id: Long,
    val name: String,
)
