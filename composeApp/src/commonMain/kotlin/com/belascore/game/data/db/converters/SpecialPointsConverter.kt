package com.belascore.game.data.db.converters

import androidx.room.TypeConverter
import com.belascore.game.domain.model.SpecialPoints
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SpecialPointsConverter {
    @TypeConverter
    fun fromSpecialPoints(specialPoints: Set<SpecialPoints>): String =
        Json.encodeToString(specialPoints)

    @TypeConverter
    fun toSpecialPoints(data: String): Set<SpecialPoints> =
        Json.decodeFromString(data)
}
