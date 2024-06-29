package com.belascore.newGame.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NewGameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NewGameUiState())
    val uiState = _uiState.asStateFlow()

    fun updatePlayerCount(playerCount: PlayerCount) {
        _uiState.update {
            it.copy(
                gameOptions =
                    it.gameOptions.copy(
                        playerCount = playerCount,
                        winningScore = playerCount.defaultScore,
                    ),
            )
        }
    }

    fun updateWinningScore(winningScore: Int) {
        _uiState.update { it.copy(gameOptions = it.gameOptions.copy(winningScore = winningScore)) }
    }
}
