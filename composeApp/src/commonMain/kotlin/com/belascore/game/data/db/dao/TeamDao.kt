package com.belascore.game.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.belascore.game.data.db.model.TeamEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {

    @Insert
    suspend fun insertTeam(team: TeamEntity): Long

    @Query("SELECT * FROM TeamEntity WHERE id = :teamId")
    fun observeTeamById(teamId: Long): Flow<TeamEntity>

    @Transaction
    @Query("SELECT * FROM TeamEntity WHERE id IN (SELECT teamId FROM GameTeamCrossRef WHERE gameId = :gameId)")
    fun observeTeamsForGame(gameId: Long): Flow<List<TeamEntity>>
}