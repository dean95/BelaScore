package com.belascore.game.domain.repository

import com.belascore.game.domain.model.Team
import kotlinx.coroutines.flow.Flow

interface TeamRepository {

    fun observeTeamsForGame(gameId: Long): Flow<List<Team>>
}
