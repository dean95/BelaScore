package com.belascore.game.domain.repository

import com.belascore.game.domain.model.Game

interface GameRepository {

    suspend fun insertGame(game: Game)
}