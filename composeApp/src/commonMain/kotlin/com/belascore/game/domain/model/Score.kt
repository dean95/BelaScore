package com.belascore.game.domain.model

data class Score(
    val gameId: Long,
    val teamId: Long,
    val roundNumber: Int,
    val score: Int
)