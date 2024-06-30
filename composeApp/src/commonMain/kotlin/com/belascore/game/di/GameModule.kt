package com.belascore.game.di

import androidx.room.RoomDatabase
import com.belascore.game.data.db.dao.GameDao
import com.belascore.game.data.db.GameDatabase
import org.koin.dsl.module

val gameModule = module {
    single<GameDao> {
        val dbBuilder = get<RoomDatabase.Builder<GameDatabase>>()
        dbBuilder.build().gameDao()
    }
}
