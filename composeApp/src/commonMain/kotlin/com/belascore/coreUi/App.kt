package com.belascore.coreUi

import androidx.compose.runtime.Composable
import com.belascore.coreUi.navigation.Navigation
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {
    KoinContext {
        Navigation()
    }
}
