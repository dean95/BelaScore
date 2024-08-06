package com.belascore.coreUi.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
actual fun OnBackPressed(enabled: Boolean, onBack: () -> Unit) {
    BackHandler(enabled) {
        onBack()
    }
}
