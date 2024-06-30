package com.belascore.game.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.belascore.game.data.db.dao.GameDao
import com.belascore.game.data.db.model.GameEntity

const val DATABASE_NAME = "game_database.db"

interface Db {
    fun clearAllTables()
}

@Database(entities = [GameEntity::class], version = 1)
abstract class GameDatabase :
    RoomDatabase(),
    Db {
    abstract fun gameDao(): GameDao

    override fun clearAllTables() {
        // no-op
    }
}
