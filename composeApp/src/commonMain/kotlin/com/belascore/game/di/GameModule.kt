package com.belascore.game.di

import androidx.room.RoomDatabase
import com.belascore.game.data.db.GameDatabase
import com.belascore.game.data.db.dao.GameDao
import com.belascore.game.data.db.mapper.DbMapper
import com.belascore.game.data.db.mapper.DbMapperImpl
import com.belascore.game.data.repository.GameRepositoryImpl
import com.belascore.game.data.repository.ScoreRepositoryImpl
import com.belascore.game.data.repository.TeamRepositoryImpl
import com.belascore.game.domain.repository.GameRepository
import com.belascore.game.domain.repository.ScoreRepository
import com.belascore.game.domain.repository.TeamRepository
import com.belascore.game.domain.useCase.InsertScoresUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val gameModule = module {
    single<GameDao> {
        val dbBuilder = get<RoomDatabase.Builder<GameDatabase>>()
        dbBuilder.build().gameDao()
    }

    singleOf(::DbMapperImpl).bind<DbMapper>()

    singleOf(::GameRepositoryImpl).bind<GameRepository>()

    singleOf(::ScoreRepositoryImpl).bind<ScoreRepository>()

    singleOf(::TeamRepositoryImpl).bind<TeamRepository>()

    singleOf(::InsertScoresUseCase)
}
