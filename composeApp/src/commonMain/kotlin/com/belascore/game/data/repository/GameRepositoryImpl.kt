package com.belascore.game.data.repository

import com.belascore.game.data.db.dao.GameDao
import com.belascore.game.data.db.model.GameEntity
import com.belascore.game.data.db.model.GameTeamCrossRef
import com.belascore.game.domain.repository.GameRepository

internal class GameRepositoryImpl(
    private val gameDao: GameDao
) : GameRepository {

    override suspend fun insertGame(winningScore: Int): Long = gameDao.insert(GameEntity(winningScore = winningScore))

    override suspend fun insertGameTeamCrossRef(gameId: Long, teamId: Long) =
        gameDao.insertGameTeamCrossRef(
            GameTeamCrossRef(
                gameId = gameId,
                teamId = teamId
            )
        )
}