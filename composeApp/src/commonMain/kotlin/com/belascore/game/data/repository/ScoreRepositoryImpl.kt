package com.belascore.game.data.repository

import com.belascore.game.data.db.dao.ScoreDao
import com.belascore.game.data.db.mapper.DbMapper
import com.belascore.game.domain.model.Score
import com.belascore.game.domain.repository.ScoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ScoreRepositoryImpl(
    private val scoreDao: ScoreDao,
    private val dbMapper: DbMapper
) : ScoreRepository {

    override suspend fun insertScores(scores: List<Score>) = scoreDao.insertScores(scores.map(dbMapper::toScoreEntity))

    override fun getScoresByGame(gameId: Long): Flow<List<Score>> =
        scoreDao
            .getScoresByGame(gameId = gameId)
            .map { it.map(dbMapper::fromScoreEntity) }
}