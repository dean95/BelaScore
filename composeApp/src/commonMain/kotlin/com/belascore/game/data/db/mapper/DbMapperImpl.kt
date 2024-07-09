package com.belascore.game.data.db.mapper

import com.belascore.game.data.db.model.GameEntity
import com.belascore.game.data.db.model.ScoreEntity
import com.belascore.game.data.db.model.TeamEntity
import com.belascore.game.domain.model.Game
import com.belascore.game.domain.model.Score
import com.belascore.game.domain.model.Team

internal class DbMapperImpl : DbMapper {
    override fun toGameEntity(game: Game) =
        GameEntity(
            id = game.id,
            winningScore = game.winningScore
        )

    override fun toTeamEntity(team: Team) =
        TeamEntity(
            id = team.id,
            name = team.name
        )

    override fun toScoreEntity(score: Score) =
        ScoreEntity(
            gameId = score.gameId,
            teamId = score.teamId,
            roundNumber = score.roundNumber,
            score = score.score
        )

    override fun fromGameEntity(entity: GameEntity) = Game(
        id = entity.id,
        winningScore = entity.winningScore
    )

    override fun fromTeamEntity(entity: TeamEntity) =
        Team(
            id = entity.id,
            name = entity.name
        )

    override fun fromScoreEntity(entity: ScoreEntity) =
        Score(
            gameId = entity.gameId,
            teamId = entity.teamId,
            roundNumber = entity.roundNumber,
            score = entity.score
        )
}