package com.belascore.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.belascore.game.data.db.DATABASE_NAME
import com.belascore.game.data.db.GameDatabase
import java.io.File

fun getDatabaseBuilder(): RoomDatabase.Builder<GameDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), DATABASE_NAME)
    return Room.databaseBuilder<GameDatabase>(
        name = dbFile.absolutePath
    ).setDriver(BundledSQLiteDriver())
}
