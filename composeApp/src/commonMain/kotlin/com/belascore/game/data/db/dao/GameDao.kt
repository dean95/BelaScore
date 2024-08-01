package com.belascore.game.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.belascore.game.data.db.model.GameEntity
import com.belascore.game.data.db.model.GameTeamCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Insert
    suspend fun insert(game: GameEntity): Long

    @Insert
    suspend fun insertGameTeamCrossRef(crossRef: GameTeamCrossRef)

    @Query("SELECT winningScore FROM GameEntity WHERE id = :gameId")
    fun observeWinningScoreForGame(gameId: Long): Flow<Int>
}
