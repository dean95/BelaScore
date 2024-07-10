package com.belascore.score.di

import com.belascore.score.ui.ScoreViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val scoreModule = module {
    viewModelOf(::ScoreViewModel)
}
