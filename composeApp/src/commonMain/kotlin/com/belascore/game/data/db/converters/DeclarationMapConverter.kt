package com.belascore.game.data.db.converters

import androidx.room.TypeConverter
import com.belascore.game.domain.model.DeclarationType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DeclarationMapConverter {
    @TypeConverter
    fun fromDeclarationMap(map: Map<DeclarationType, Int>): String {
        return Json.encodeToString(map)
    }

    @TypeConverter
    fun toDeclarationMap(data: String): Map<DeclarationType, Int> {
        return Json.decodeFromString(data)
    }
}
