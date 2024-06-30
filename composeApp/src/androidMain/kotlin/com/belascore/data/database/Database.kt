package com.belascore.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.belascore.game.data.db.DATABASE_NAME
import com.belascore.game.data.db.GameDatabase

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<GameDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(DATABASE_NAME)
    return Room.databaseBuilder(
        context = appContext,
        name = dbFile.absolutePath
    )
}
