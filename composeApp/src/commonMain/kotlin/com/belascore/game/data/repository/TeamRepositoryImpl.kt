package com.belascore.game.data.repository

import com.belascore.game.data.db.dao.TeamDao
import com.belascore.game.data.db.model.TeamEntity
import com.belascore.game.domain.repository.TeamRepository

internal class TeamRepositoryImpl(
    private val teamDao: TeamDao
) : TeamRepository {

    override suspend fun insertTeam(name: String): Long = teamDao.insertTeam(TeamEntity(name = name))
}