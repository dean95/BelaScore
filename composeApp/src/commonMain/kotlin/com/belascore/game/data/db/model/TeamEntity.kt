package com.belascore.game.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TeamEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String
)