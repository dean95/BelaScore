package com.belascore.score.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belascore.game.domain.model.Score
import com.belascore.game.domain.model.SpecialPoints
import com.belascore.game.domain.repository.GameRepository
import com.belascore.game.domain.repository.ScoreRepository
import com.belascore.game.domain.repository.TeamRepository
import com.belascore.score.ui.components.TeamScores
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
    private val gameRepository: GameRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScoreUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeTeamsAndScores()
        observeWinningTeams()
        observeGame()
    }

    fun updateScores(
        teamScores: TeamScores,
        roundNumber: Int
    ) = viewModelScope.launch {
        scoreRepository.insertScores(
            teamScores.scores.map { (teamId, score) ->
                Score(
                    gameId = gameId,
                    teamId = teamId,
                    score = score.score +
                            score
                                .declarations
                                .map { (declaration, count) -> declaration.points * count }
                                .sum()
                            +
                            score.specialPoints.sumOf(SpecialPoints::points),
                    roundNumber = roundNumber
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
                .mapValues { (_, scores) -> scores.sumOf(Score::score) }

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
                    scores = teamScores.mapValues { (_, score) -> score.score }
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
                .mapValues { (_, scores) -> scores.sumOf(Score::score) }

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
