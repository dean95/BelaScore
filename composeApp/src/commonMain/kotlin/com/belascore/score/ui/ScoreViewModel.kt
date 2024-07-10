package com.belascore.score.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belascore.game.domain.repository.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScoreViewModel(
    private val gameId: Long,
    private val teamRepository: TeamRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScoreUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            teamRepository
                .getTeamsForGame(gameId)
                .collect { teams ->
                    _uiState.update {
                        it.copy(
                            teams = teams.map { team ->
                                TeamUiState(
                                    id = team.id,
                                    name = team.name
                                )
                            }
                        )
                    }
                }
        }
    }
}