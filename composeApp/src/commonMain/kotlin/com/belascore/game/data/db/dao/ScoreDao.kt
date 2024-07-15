package com.belascore.game.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.belascore.game.data.db.model.ScoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {

    @Insert
    suspend fun insertScores(scores: List<ScoreEntity>)

    @Query("SELECT * FROM scoreEntity WHERE gameId = :gameId")
    fun getScoresByGame(gameId: Long): Flow<List<ScoreEntity>>
}