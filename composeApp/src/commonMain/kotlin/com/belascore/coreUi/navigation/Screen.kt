package com.belascore.coreUi.navigation

import kotlinx.serialization.Serializable

@Serializable
data object Home

@Serializable
data object NewGame

@Serializable
data class Score(
    val gameId: Long,
    val source: String? = null,
    val showGameResultDialog: Boolean = false
)

@Serializable
data object GameHistory
