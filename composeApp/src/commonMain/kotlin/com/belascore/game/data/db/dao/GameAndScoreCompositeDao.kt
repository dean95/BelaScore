package com.belascore.game.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface GameAndScoreCompositeDao {

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
}
