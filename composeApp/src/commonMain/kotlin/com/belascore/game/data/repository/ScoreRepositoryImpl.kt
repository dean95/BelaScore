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

    override suspend fun insertScore(score: Score) = scoreDao.insertScore(dbMapper.toScoreEntity(score))

    override fun getScoresByGameAndTeam(gameId: Int, teamId: Int): Flow<List<Score>> =
        scoreDao
            .getScoresByGameAndTeam(
                gameId = gameId,
                teamId = teamId
            )
            .map { it.map(dbMapper::fromScoreEntity) }
}