package com.belascore.game.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.belascore.game.data.db.model.GameEntity
import com.belascore.game.data.db.model.GameTeamCrossRef
import com.belascore.game.data.db.model.ScoreEntity
import com.belascore.game.data.db.model.TeamEntity
import kotlinx.coroutines.flow.Flow

/**
 * Single DAO for the entire database. This is a workaround for issues encountered when
 * observing data modified within transactions spanning multiple DAOs.
 * Consolidating operations into a single DAO ensures consistency in data observation.
 */

@Dao
interface GameDao {

    // --- Game ---

    @Insert
    suspend fun insertGame(game: GameEntity): Long

    @Query("SELECT * FROM GameEntity WHERE isInProgress = 1")
    fun observeActiveGame(): Flow<GameEntity?>

    @Query("UPDATE GameEntity SET isInProgress = 0 WHERE id = :gameId")
    suspend fun endGame(gameId: Long)

    @Query("SELECT winningScore FROM GameEntity WHERE id = :gameId")
    fun observeWinningScoreForGame(gameId: Long): Flow<Int>

    @Query("DELETE FROM GameEntity WHERE id = :gameId")
    suspend fun deleteGame(gameId: Long)

    @Query("SELECT * FROM GameEntity WHERE id = :gameId")
    fun observeGameById(gameId: Long): Flow<GameEntity?>

    // --- GameTeamCrossRef ---

    @Insert
    suspend fun insertGameTeamCrossRefs(crossRef: List<GameTeamCrossRef>)

    @Query("DELETE FROM GameTeamCrossRef WHERE gameId = :gameId")
    suspend fun deleteGameTeamCrossRefsForGame(gameId: Long)

    // --- Score ---

    @Insert
    suspend fun insertScores(scores: List<ScoreEntity>)

    @Query("SELECT * FROM scoreEntity WHERE gameId = :gameId")
    fun observeScoresByGame(gameId: Long): Flow<List<ScoreEntity>>

    @Query("DELETE FROM scoreEntity WHERE gameId = :gameId")
    suspend fun deleteScoresForGame(gameId: Long)

    // --- Team ---

    @Insert
    suspend fun insertTeams(teams: List<TeamEntity>): List<Long>

    @Query("SELECT * FROM TeamEntity WHERE id = :teamId")
    fun observeTeamById(teamId: Long): Flow<TeamEntity>

    @Query("SELECT * FROM TeamEntity WHERE id IN (SELECT teamId FROM GameTeamCrossRef WHERE gameId = :gameId)")
    fun observeTeamsForGame(gameId: Long): Flow<List<TeamEntity>>

    // --- Transaction ---

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

    @Transaction
    suspend fun deleteGameWithScores(gameId: Long) {
        deleteScoresForGame(gameId)
        deleteGameTeamCrossRefsForGame(gameId)
        deleteGame(gameId)
    }
}

