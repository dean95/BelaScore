package com.belascore.game.domain.repository

import com.belascore.game.domain.model.Team
import kotlinx.coroutines.flow.Flow

interface GameRepository {

    suspend fun insertGame(winningScore: Int): Long

    suspend fun insertGameTeamCrossRef(gameId: Long, teamId: Long)

    fun observeWinningTeams(gameId: Long): Flow<List<Team>>

    suspend fun endGame(gameId: Long)

    suspend fun deleteGame(gameId: Long)
}
