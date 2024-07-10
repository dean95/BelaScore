package com.belascore.game.domain.repository

import com.belascore.game.domain.model.Team
import kotlinx.coroutines.flow.Flow

interface TeamRepository {

    suspend fun insertTeam(name: String): Long

    fun getTeamsForGame(gameId: Long): Flow<List<Team>>
}