package com.belascore.score.ui

import com.belascore.newGame.ui.PlayerCount

data class ScoreUiState(
    val teams: List<TeamUiState> = emptyList(),
    val rounds: List<RoundItemUiState> = emptyList(),
    val winningTeams: List<TeamUiState> = emptyList(),
    val quitGame: Boolean = false,
    val playerCount: PlayerCount = PlayerCount.TWO
)

data class RoundItemUiState(
    val roundNumber: Int,
    val scores: Map<Long, Int>
)

data class TeamUiState(
    val id: Long,
    val name: String
)
