package com.belascore.game.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction
import com.belascore.game.data.db.model.GameEntity
import com.belascore.game.data.db.model.GameTeamCrossRef
import com.belascore.game.data.db.model.TeamEntity

@Dao
interface GameAndTeamCompositeDao {

    @Insert
    suspend fun insertGame(game: GameEntity): Long

    @Insert
    suspend fun insertGameTeamCrossRef(crossRef: GameTeamCrossRef)

    @Insert
    suspend fun insertTeam(team: TeamEntity): Long

    @Transaction
    suspend fun insertGameWithTeams(game: GameEntity, teams: List<TeamEntity>): Long {
        val gameId = insertGame(game)

        teams.forEach { team ->
            val teamId = insertTeam(team)
            insertGameTeamCrossRef(
                GameTeamCrossRef(
                    gameId = gameId,
                    teamId = teamId
                )
            )
        }

        return gameId
    }
}
