package com.belascore.game.domain.repository

import com.belascore.game.domain.model.Game
import com.belascore.game.domain.model.Team
import com.belascore.newGame.ui.PlayerCount
import kotlinx.coroutines.flow.Flow

interface GameRepository {

    suspend fun insertGameWithTeams(
        winningScore: Int,
        playerCount: PlayerCount,
        teamNames: List<String>
    ): Long

    fun observeWinningTeams(gameId: Long): Flow<List<Team>>

    suspend fun endGame(gameId: Long)

    suspend fun deleteGame(gameId: Long)

    fun observeActiveGame(): Flow<Game?>

    fun observeGameById(gameId: Long): Flow<Game>
}
