package com.belascore.gameHistory.di

import com.belascore.gameHistory.ui.GameHistoryViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val gameHistoryModule = module {
    viewModelOf(::GameHistoryViewModel)
}
