package com.belascore.game.data.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.belascore.game.data.db.converters.DeclarationMapConverter
import com.belascore.game.data.db.converters.SpecialPointsConverter
import com.belascore.game.data.db.dao.GameDao
import com.belascore.game.data.db.model.GameEntity
import com.belascore.game.data.db.model.GameTeamCrossRef
import com.belascore.game.data.db.model.ScoreEntity
import com.belascore.game.data.db.model.TeamEntity

const val DATABASE_NAME = "game_database.db"

@Database(
    entities = [GameEntity::class, TeamEntity::class, ScoreEntity::class, GameTeamCrossRef::class],
    version = 1
)
@TypeConverters(DeclarationMapConverter::class, SpecialPointsConverter::class)
@ConstructedBy(GameDatabaseConstructor::class)
abstract class GameDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object GameDatabaseConstructor : RoomDatabaseConstructor<GameDatabase>
