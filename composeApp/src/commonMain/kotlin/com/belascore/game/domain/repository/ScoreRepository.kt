package com.belascore.game.domain.repository

import com.belascore.game.domain.model.Score
import kotlinx.coroutines.flow.Flow

interface ScoreRepository {

    suspend fun insertScores(scores: List<Score>)

    fun observeScoresByGame(gameId: Long): Flow<List<Score>>
}