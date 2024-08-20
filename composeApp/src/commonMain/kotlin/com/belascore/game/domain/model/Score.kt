package com.belascore.game.domain.model

data class Score(
    val gameId: Long,
    val teamId: Long,
    val roundNumber: Int,
    val baseScore: Int,
    val totalScore: Int,
    val declarations: Map<DeclarationType, Int>,
    val specialPoints: Set<SpecialPoints>
)
