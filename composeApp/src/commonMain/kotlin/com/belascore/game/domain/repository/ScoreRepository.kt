package com.belascore.game.domain.repository

import com.belascore.game.domain.model.Score
import kotlinx.coroutines.flow.Flow

interface ScoreRepository {

    suspend fun insertScore(score: Score)

    fun getScoresByGameAndTeam(gameId: Int, teamId: Int): Flow<List<Score>>
}