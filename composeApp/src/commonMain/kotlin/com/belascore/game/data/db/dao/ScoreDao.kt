package com.belascore.game.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.belascore.game.data.db.model.ScoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {

    @Insert
    suspend fun insertScore(score: ScoreEntity): Long

    @Query("SELECT * FROM scoreEntity WHERE gameId = :gameId AND teamId = :teamId")
    fun getScoresByGameAndTeam(gameId: Int, teamId: Int): Flow<List<ScoreEntity>>
}