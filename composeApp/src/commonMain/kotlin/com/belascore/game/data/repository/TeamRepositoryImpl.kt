package com.belascore.game.data.repository

import com.belascore.game.data.db.dao.TeamDao
import com.belascore.game.data.db.mapper.DbMapper
import com.belascore.game.data.db.model.TeamEntity
import com.belascore.game.domain.model.Team
import com.belascore.game.domain.repository.TeamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class TeamRepositoryImpl(
    private val teamDao: TeamDao,
    private val dbMapper: DbMapper
) : TeamRepository {

    override suspend fun insertTeam(name: String): Long = teamDao.insertTeam(TeamEntity(name = name))

    override fun getTeamsForGame(gameId: Long): Flow<List<Team>> =
        teamDao
            .getTeamsForGame(gameId)
            .map { teams ->
                teams.map(dbMapper::fromTeamEntity)
            }
}