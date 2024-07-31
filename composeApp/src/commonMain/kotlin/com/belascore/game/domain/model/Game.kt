package com.belascore.game.domain.model

const val MAX_SCORE_WITHOUT_SPECIAL_POINTS = 162

data class Game(
    val id: Long,
    val winningScore: Int
)