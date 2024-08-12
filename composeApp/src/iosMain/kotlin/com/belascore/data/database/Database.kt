package com.belascore.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.belascore.game.data.db.DATABASE_NAME
import com.belascore.game.data.db.GameDatabase
import platform.Foundation.NSHomeDirectory

fun getDatabaseBuilder(): RoomDatabase.Builder<GameDatabase> {
    val dbFilePath = NSHomeDirectory() + "/$DATABASE_NAME"
    return Room.databaseBuilder<GameDatabase>(
        name = dbFilePath
    ).setDriver(BundledSQLiteDriver())
}
