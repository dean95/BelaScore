package com.belascore.score.ui

import com.belascore.newGame.ui.PlayerCount
import com.belascore.score.ui.components.RoundScores

data class ScoreUiState(
    val teams: List<TeamUiState> = emptyList(),
    val rounds: List<RoundItemUiState> = emptyList(),
    val winningTeams: List<TeamUiState> = emptyList(),
    val quitGame: Boolean = false,
    val playerCount: PlayerCount = PlayerCount.TWO
)

data class RoundItemUiState(
    val roundScores: RoundScores
)

data class TeamUiState(
    val id: Long,
    val name: String,
    val totalScore: Int
)

sealed class DialogState {
    data object None : DialogState()
    data class ScoreInput(val roundScores: RoundScores? = null) : DialogState()
    data object GameResult : DialogState()
    data object QuitConfirmation : DialogState()
}
