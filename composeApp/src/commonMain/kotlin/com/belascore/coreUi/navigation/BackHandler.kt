package com.belascore.coreUi.navigation

import androidx.compose.runtime.Composable

@Composable
expect fun OnBackPressed(enabled: Boolean, onBack: () -> Unit)
