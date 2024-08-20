package com.belascore.game.data.repository

import com.belascore.game.data.db.dao.GameDao
import com.belascore.game.data.db.mapper.DbMapper
import com.belascore.game.domain.model.Score
import com.belascore.game.domain.repository.ScoreRepository
import com.belascore.game.domain.useCase.InsertScoresUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ScoreRepositoryImpl(
    private val gameDao: GameDao,
    private val dbMapper: DbMapper
) : ScoreRepository {

    override suspend fun insertScores(scores: List<InsertScoresUseCase.Param>) =
        gameDao.insertScores(
            scores.map {
                dbMapper.toScoreEntity(
                    gameId = it.gameId,
                    teamId = it.teamId,
                    roundNumber = it.roundNumber,
                    baseScore = it.score,
                    declarations = it.declarations,
                    specialPoints = it.specialPoints
                )
            }
        )

    override fun observeScoresByGame(gameId: Long): Flow<List<Score>> =
        gameDao
            .observeScoresByGame(gameId = gameId)
            .map { it.map(dbMapper::fromScoreEntity) }
}
