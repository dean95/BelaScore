package com.belascore.game.data.db.mapper

import com.belascore.game.data.db.model.GameEntity
import com.belascore.game.data.db.model.ScoreEntity
import com.belascore.game.data.db.model.TeamEntity
import com.belascore.game.domain.model.Game
import com.belascore.game.domain.model.Score
import com.belascore.game.domain.model.Team

interface DbMapper {

    fun toGameEntity(game: Game): GameEntity

    fun toTeamEntity(team: Team): TeamEntity

    fun toScoreEntity(score: Score): ScoreEntity

    fun fromGameEntity(entity: GameEntity): Game

    fun fromTeamEntity(entity: TeamEntity): Team

    fun fromScoreEntity(entity: ScoreEntity): Score
}