package com.belascore.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belascore.game.domain.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val gameRepository: GameRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeActiveGame()
    }

    private fun observeActiveGame() = viewModelScope.launch {
        gameRepository
            .observeActiveGame()
            .collect { game ->
                println(game)
                _uiState.update { homeUiState ->
                    homeUiState.copy(
                        activeGameId = game?.id
                    )
                }
            }
    }
}
