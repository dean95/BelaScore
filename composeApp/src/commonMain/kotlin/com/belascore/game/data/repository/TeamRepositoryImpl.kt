package com.belascore.game.data.repository

import com.belascore.game.data.db.dao.TeamDao
import com.belascore.game.data.db.mapper.DbMapper
import com.belascore.game.domain.model.Team
import com.belascore.game.domain.repository.TeamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class TeamRepositoryImpl(
    private val teamDao: TeamDao,
    private val dbMapper: DbMapper
) : TeamRepository {

    override fun observeTeamsForGame(gameId: Long): Flow<List<Team>> =
        teamDao
            .observeTeamsForGame(gameId)
            .map { teams ->
                teams.map(dbMapper::fromTeamEntity)
            }
}
