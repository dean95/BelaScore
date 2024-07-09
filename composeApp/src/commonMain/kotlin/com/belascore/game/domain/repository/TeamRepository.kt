package com.belascore.game.domain.repository

import com.belascore.game.domain.model.Team

interface TeamRepository {

    suspend fun insertTeam(team: Team)
}