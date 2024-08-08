package com.belascore.game.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.belascore.game.data.db.model.GameEntity
import com.belascore.game.data.db.model.GameTeamCrossRef
import com.belascore.game.data.db.model.TeamEntity
import kotlinx.coroutines.flow.Flow

/**
 * This DAO handles both transactional operations and data observation. This is due to an issue
 * where observing data modified within a transaction would not reflect changes consistently when
 * the operations were split across multiple DAOs. Consolidating them here resolved the issue.
 */
@Dao
interface GameCompositeDao {

    @Query("DELETE FROM GameEntity WHERE id = :gameId")
    suspend fun deleteGame(gameId: Long)

    @Query("DELETE FROM GameTeamCrossRef WHERE gameId = :gameId")
    suspend fun deleteGameTeamCrossRefsForGame(gameId: Long)

    @Query("DELETE FROM scoreEntity WHERE gameId = :gameId")
    suspend fun deleteScoresForGame(gameId: Long)

    @Transaction
    suspend fun deleteGameWithScores(gameId: Long) {
        deleteScoresForGame(gameId)
        deleteGameTeamCrossRefsForGame(gameId)
        deleteGame(gameId)
    }

    @Query("SELECT * FROM GameEntity WHERE isInProgress = 1")
    fun observeActiveGame(): Flow<GameEntity?>

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
