package com.belascore.core.di

import com.belascore.game.di.gameModule
import com.belascore.home.di.homeModule
import com.belascore.newGame.di.newGameModule
import com.belascore.score.di.scoreModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

expect val platformModule: Module

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            gameModule,
            platformModule,
            newGameModule,
            scoreModule,
            homeModule
        )
    }
}
