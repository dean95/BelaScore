package com.belascore.game.domain.model

data class Score(
    val gameId: Int,
    val teamId: Int,
    val roundNumber: Int,
    val score: Int
)