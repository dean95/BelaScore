package com.belascore.gameHistory.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belascore.game.domain.model.Score
import com.belascore.game.domain.repository.GameRepository
import com.belascore.game.domain.repository.ScoreRepository
import com.belascore.game.domain.repository.TeamRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class GameHistoryViewModel(
    private val gameRepository: GameRepository,
    private val teamRepository: TeamRepository,
    private val scoreRepository: ScoreRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameHistoryUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observePastGames()
    }

    fun deleteGame(gameId: Long) = viewModelScope.launch {
        gameRepository.deleteGame(gameId)
    }

    private fun observePastGames() = viewModelScope.launch {
        gameRepository
            .observePastGames()
            .flatMapLatest { games ->
                if (games.isEmpty()) {
                    flow {
                        emit(emptyList())
                    }
                } else {
                    combine(
                        games
                            .map { game ->
                                combine(
                                    scoreRepository
                                        .observeScoresByGame(game.id)
                                        .map { it.groupBy(Score::teamId) },
                                    teamRepository.observeTeamsForGame(game.id)
                                ) { scoresByTeam, teams ->
                                    val teamUiStates = teams.map {
                                        TeamUiState(
                                            id = it.id,
                                            name = it.name,
                                            score = scoresByTeam
                                                .getValue(it.id)
                                                .sumOf(Score::totalScore)
                                        )
                                    }
                                    GameSummaryUiState(
                                        id = game.id,
                                        teams = teamUiStates
                                    )
                                }
                            }, Array<GameSummaryUiState>::toList
                    )
                }
            }
            .collect { gameSummaryUiStates ->
                _uiState.update {
                    it.copy(
                        games = gameSummaryUiStates
                    )
                }
            }
    }
}
