package com.belascore.newGame.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belascore.game.domain.repository.GameRepository
import com.belascore.game.domain.repository.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewGameViewModel(
    private val gameRepository: GameRepository,
    private val teamRepository: TeamRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(NewGameUiState())
    val uiState = _uiState.asStateFlow()

    fun updatePlayerCount(playerCount: PlayerCount) {
        _uiState.update {
            it.copy(
                gameOptions =
                it.gameOptions.copy(
                    playerCount = playerCount,
                    winningScore = playerCount.defaultScore
                )
            )
        }
    }

    fun updateWinningScore(winningScore: Int) {
        _uiState.update { it.copy(gameOptions = it.gameOptions.copy(winningScore = winningScore)) }
    }

    fun createNewGame(
        winningScore: Int,
        teamNames: List<String>,
        onStartGameClick: (Long) -> Unit
    ) = viewModelScope.launch {
        val gameId = gameRepository.insertGame(winningScore)
        teamNames.forEach {
            val teamId = teamRepository.insertTeam(it)
            gameRepository.insertGameTeamCrossRef(gameId, teamId)
        }
        onStartGameClick(gameId)
    }
}
