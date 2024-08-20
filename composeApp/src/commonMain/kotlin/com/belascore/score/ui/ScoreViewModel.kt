package com.belascore.score.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belascore.game.domain.model.Score
import com.belascore.game.domain.repository.GameRepository
import com.belascore.game.domain.repository.ScoreRepository
import com.belascore.game.domain.repository.TeamRepository
import com.belascore.game.domain.useCase.InsertScoresUseCase
import com.belascore.score.ui.components.RoundScores
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScoreViewModel(
    private val gameId: Long,
    private val teamRepository: TeamRepository,
    private val scoreRepository: ScoreRepository,
    private val gameRepository: GameRepository,
    private val insertScoresUseCase: InsertScoresUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScoreUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeTeamsAndScores()
        observeWinningTeams()
        observeGame()
    }

    fun updateScores(
        roundScores: RoundScores,
        roundNumber: Int
    ) = viewModelScope.launch {
        insertScoresUseCase(
            roundScores.scores.map { (teamId, score) ->
                InsertScoresUseCase.Param(
                    gameId = gameId,
                    teamId = teamId,
                    score = score.score,
                    roundNumber = roundNumber,
                    declarations = score.declarations,
                    specialPoints = score.specialPoints
                )
            }
        )
    }

    fun quitGame() = viewModelScope.launch {
        gameRepository.deleteGame(gameId)
        _uiState.update { scoreUiState ->
            scoreUiState.copy(
                quitGame = true
            )
        }
    }

    private fun observeGame() = viewModelScope.launch {
        gameRepository
            .observeGameById(gameId)
            .collect { game ->
                _uiState.update { scoreUiState ->
                    scoreUiState.copy(
                        playerCount = game.playerCount
                    )
                }
            }
    }

    private fun observeTeamsAndScores() = viewModelScope.launch {
        combine(
            teamRepository.observeTeamsForGame(gameId),
            scoreRepository.observeScoresByGame(gameId)
        ) { teams, scores ->
            val teamTotalScores = scores
                .groupBy(Score::teamId)
                .mapValues { (_, scores) -> scores.sumOf(Score::totalScore) }

            val teamItems = teams.map { team ->
                TeamUiState(
                    id = team.id,
                    name = team.name,
                    totalScore = teamTotalScores[team.id] ?: 0
                )
            }

            val groupedByRound = scores.groupBy(Score::roundNumber)

            val roundItems = groupedByRound.map { (round, scores) ->
                val teamScores = scores.associateBy(Score::teamId)
                RoundItemUiState(
                    roundNumber = round,
                    scores = teamScores.mapValues { (_, score) -> score.totalScore }
                )
            }.sortedBy(RoundItemUiState::roundNumber)

            _uiState.update {
                it.copy(
                    teams = teamItems,
                    rounds = roundItems
                )
            }
        }.collect()
    }

    private fun observeWinningTeams() = viewModelScope.launch {
        combine(
            scoreRepository.observeScoresByGame(gameId),
            gameRepository
                .observeWinningTeams(gameId)
                .distinctUntilChanged()
        ) { scores, winningTeams ->
            val teamTotalScores = scores
                .groupBy(Score::teamId)
                .mapValues { (_, scores) -> scores.sumOf(Score::totalScore) }

            if (winningTeams.isNotEmpty()) {
                endGame()
            }

            _uiState.update { scoreUiState ->
                scoreUiState.copy(
                    winningTeams = winningTeams.map {
                        TeamUiState(
                            id = it.id,
                            name = it.name,
                            totalScore = teamTotalScores[it.id] ?: 0
                        )
                    }
                )
            }
        }.collect()
    }

    private fun endGame() = viewModelScope.launch {
        gameRepository.endGame(gameId)
    }
}
