package com.belascore.game.data.repository

import com.belascore.game.data.db.dao.GameDao
import com.belascore.game.data.db.mapper.DbMapper
import com.belascore.game.domain.model.Game
import com.belascore.game.domain.repository.GameRepository

internal class GameRepositoryImpl(
    private val gameDao: GameDao,
    private val dbMapper: DbMapper
) : GameRepository {

    override suspend fun insertGame(game: Game) = gameDao.insert(dbMapper.toGameEntity(game))
}