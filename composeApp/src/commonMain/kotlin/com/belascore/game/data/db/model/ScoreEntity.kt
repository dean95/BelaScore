package com.belascore.game.data.db.model

import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["gameId", "teamId", "roundNumber"],
    indices = [Index(value = ["gameId"])]
)
data class ScoreEntity(
    val gameId: Long,
    val teamId: Long,
    val roundNumber: Int,
    val score: Int
)
