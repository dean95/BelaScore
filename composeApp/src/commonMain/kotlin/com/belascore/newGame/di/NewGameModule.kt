package com.belascore.newGame.di

import com.belascore.newGame.ui.NewGameViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val newGameModule = module {
    viewModelOf(::NewGameViewModel)
}
