package com.belascore.game.data.db.model

import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["gameId", "teamId"],
    indices = [
        Index(value = ["gameId"]),
        Index(value = ["teamId"])
    ]
)
data class GameTeamCrossRef(
    val gameId: Long,
    val teamId: Long
)
