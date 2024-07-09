package com.belascore.game.data.db.model

import androidx.room.Entity

@Entity(primaryKeys = ["gameId", "teamId"])
data class ScoreEntity(
    val gameId: Int,
    val teamId: Int,
    val roundNumber: Int,
    val score: Int
)