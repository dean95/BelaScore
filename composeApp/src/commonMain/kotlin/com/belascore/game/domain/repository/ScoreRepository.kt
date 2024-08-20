package com.belascore.game.domain.repository

import com.belascore.game.domain.model.Score
import com.belascore.game.domain.useCase.InsertScoresUseCase
import kotlinx.coroutines.flow.Flow

interface ScoreRepository {

    suspend fun insertScores(scores: List<InsertScoresUseCase.Param>)

    fun observeScoresByGame(gameId: Long): Flow<List<Score>>
}
