package com.belascore.game.domain.repository

interface GameRepository {

    suspend fun insertGame(winningScore: Int): Long

    suspend fun insertGameTeamCrossRef(gameId: Long, teamId: Long)
}