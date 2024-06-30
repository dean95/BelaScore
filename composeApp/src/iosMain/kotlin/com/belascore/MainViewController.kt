package com.belascore

import androidx.compose.ui.window.ComposeUIViewController
import com.belascore.core.di.initKoin
import com.belascore.coreUi.App

fun MainViewController() =
    ComposeUIViewController(
        configure = { initKoin() },
    ) {
        App()
    }
