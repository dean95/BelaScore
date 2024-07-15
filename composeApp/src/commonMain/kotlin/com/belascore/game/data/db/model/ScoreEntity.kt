package com.belascore.game.data.db.model

import androidx.room.Entity

@Entity(primaryKeys = ["gameId", "teamId", "roundNumber"])
data class ScoreEntity(
    val gameId: Long,
    val teamId: Long,
    val roundNumber: Int,
    val score: Int
)