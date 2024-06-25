package com.belascore.score.ui

data class RoundItemUiState(
    val roundNumber: Int,
    val scores: Map<String, Int>,
)

data class TeamUiState(
    val id: String,
    val name: String,
)
