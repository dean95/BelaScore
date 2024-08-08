package com.belascore.game.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Query("SELECT winningScore FROM GameEntity WHERE id = :gameId")
    fun observeWinningScoreForGame(gameId: Long): Flow<Int>

    @Query("UPDATE GameEntity SET isInProgress = 0 WHERE id = :gameId")
    suspend fun endGame(gameId: Long)
}
