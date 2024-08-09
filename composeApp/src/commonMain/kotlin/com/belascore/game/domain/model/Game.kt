package com.belascore.game.domain.model

import com.belascore.newGame.ui.PlayerCount

const val TOTAL_SCORE_WITHOUT_SPECIAL_POINTS = 162

data class Game(
    val id: Long,
    val winningScore: Int,
    val playerCount: PlayerCount,
    val isInProgress: Boolean
)
