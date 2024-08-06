package com.belascore.score.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belascore.game.domain.model.DeclarationType
import com.belascore.game.domain.model.Score
import com.belascore.game.domain.model.SpecialPoints
import com.belascore.game.domain.repository.GameRepository
import com.belascore.game.domain.repository.ScoreRepository
import com.belascore.game.domain.repository.TeamRepository
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
    }

    fun updateScores(
        teamScores: Map<Long, Int>,
        teamDeclarations: Map<Long, Map<DeclarationType, Int>>,
        teamSpecialPoints: Map<Long, Set<SpecialPoints>>,
        roundNumber: Int
    ) = viewModelScope.launch {
        val declarationsScoresByTeam = teamDeclarations.mapValues { (_, declarations) ->
            declarations.map { (declaration, count) -> declaration.points * count }.sum()
        }

        val specialPointsScoresByTeam =
            teamSpecialPoints.mapValues { (_, specialPoints) -> specialPoints.sumOf(SpecialPoints::points) }

        scoreRepository.insertScores(
            teamScores.map { (teamId, score) ->
                Score(
                    gameId = gameId,
                    teamId = teamId,
                    score = score +
                            declarationsScoresByTeam.getValue(teamId) +
                            specialPointsScoresByTeam.getValue(teamId),
                    roundNumber = roundNumber
                )
            }
        )
    }

    fun quitGame() = viewModelScope.launch {
        gameRepository.endGame(gameId)
        // TODO Delete game data for the unfinished game
    }

    private fun observeTeamsAndScores() = viewModelScope.launch {
        combine(
            teamRepository.observeTeamsForGame(gameId),
            scoreRepository.observeScoresByGame(gameId)
        ) { teams, scores ->
            val teamItems = teams.map { team ->
                TeamUiState(
                    id = team.id,
                    name = team.name
                )
            }

            val groupedByRound = scores.groupBy { it.roundNumber }

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
        gameRepository
            .observeWinningTeams(gameId)
            .distinctUntilChanged()
            .collect { winningTeams ->
                if (winningTeams.isNotEmpty()) {
                    endGame()
                }
                _uiState.update { scoreUiState ->
                    scoreUiState.copy(
                        winningTeams = winningTeams.map {
                            TeamUiState(
                                id = it.id,
                                name = it.name
                            )
                        }
                    )
                }
            }
    }

    private fun endGame() = viewModelScope.launch {
        gameRepository.endGame(gameId)
    }
}
