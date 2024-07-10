package com.belascore.game.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import com.belascore.game.data.db.model.GameEntity

@Dao
interface GameDao {

    @Insert
    suspend fun insert(game: GameEntity): Long
}
