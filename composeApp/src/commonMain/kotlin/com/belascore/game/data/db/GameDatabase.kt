package com.belascore.game.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.belascore.game.data.db.dao.GameAndScoreCompositeDao
import com.belascore.game.data.db.dao.GameAndTeamCompositeDao
import com.belascore.game.data.db.dao.GameDao
import com.belascore.game.data.db.dao.ScoreDao
import com.belascore.game.data.db.dao.TeamDao
import com.belascore.game.data.db.model.GameEntity
import com.belascore.game.data.db.model.GameTeamCrossRef
import com.belascore.game.data.db.model.ScoreEntity
import com.belascore.game.data.db.model.TeamEntity

const val DATABASE_NAME = "game_database.db"

interface Db {
    fun clearAllTables()
}

@Database(
    entities = [GameEntity::class, TeamEntity::class, ScoreEntity::class, GameTeamCrossRef::class],
    version = 1
)
abstract class GameDatabase : RoomDatabase(), Db {
    abstract fun gameDao(): GameDao

    abstract fun teamDao(): TeamDao

    abstract fun scoreDao(): ScoreDao

    abstract fun gameAndTeamCompositeDao(): GameAndTeamCompositeDao

    abstract fun gameAndScoreCompositeDao(): GameAndScoreCompositeDao

    override fun clearAllTables() {
        // no-op
    }
}
