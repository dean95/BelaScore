package com.belascore.game.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import com.belascore.game.data.db.model.TeamEntity

@Dao
interface TeamDao {

    @Insert
    suspend fun insertTeam(team: TeamEntity)
}