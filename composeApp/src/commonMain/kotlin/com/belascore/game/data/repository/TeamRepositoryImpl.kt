package com.belascore.game.data.repository

import com.belascore.game.data.db.dao.TeamDao
import com.belascore.game.data.db.mapper.DbMapper
import com.belascore.game.domain.model.Team
import com.belascore.game.domain.repository.TeamRepository

internal class TeamRepositoryImpl(
    private val teamDao: TeamDao,
    private val dbMapper: DbMapper
) : TeamRepository {

    override suspend fun insertTeam(team: Team) = teamDao.insertTeam(dbMapper.toTeamEntity(team))
}