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
    suspend fun insertGameTeamCrossRefs(crossRef: List<GameTeamCrossRef>)

    @Insert
    suspend fun insertTeams(teams: List<TeamEntity>): List<Long>

    @Transaction
    suspend fun insertGameWithTeams(game: GameEntity, teams: List<TeamEntity>): Long {
        val gameId = insertGame(game)

        val teamIds = insertTeams(teams)
        val crossRefs = teamIds.map { teamId ->
            GameTeamCrossRef(
                gameId = gameId,
                teamId = teamId
            )
        }

        insertGameTeamCrossRefs(crossRefs)

        return gameId
    }
}
