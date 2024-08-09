package com.belascore.game.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GameEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val winningScore: Int,
    val numberOfPlayers: Int,
    val isInProgress: Boolean
)
