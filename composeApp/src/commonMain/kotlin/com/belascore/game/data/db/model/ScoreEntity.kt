package com.belascore.game.data.db.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.TypeConverters
import com.belascore.game.data.db.converters.DeclarationMapConverter
import com.belascore.game.data.db.converters.SpecialPointsConverter
import com.belascore.game.domain.model.DeclarationType
import com.belascore.game.domain.model.SpecialPoints

@Entity(
    primaryKeys = ["gameId", "teamId", "roundNumber"],
    indices = [Index(value = ["gameId"])]
)
data class ScoreEntity(
    val gameId: Long,
    val teamId: Long,
    val roundNumber: Int,
    val baseScore: Int,
    val totalScore: Int,
    @TypeConverters(DeclarationMapConverter::class)
    val declarations: Map<DeclarationType, Int>,
    @TypeConverters(SpecialPointsConverter::class)
    val specialPoints: Set<SpecialPoints>
)
