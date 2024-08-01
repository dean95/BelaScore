package com.belascore.score.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belascore.game.domain.model.DeclarationType
import com.belascore.game.domain.model.Score
import com.belascore.game.domain.model.SpecialPoints
import com.belascore.game.domain.repository.ScoreRepository
import com.belascore.game.domain.repository.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScoreViewModel(
    private val gameId: Long,
    private val teamRepository: TeamRepository,
    private val scoreRepository: ScoreRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScoreUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                teamRepository.getTeamsForGame(gameId),
                scoreRepository.getScoresByGame(gameId)
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
}