package com.belascore.game.data.db.mapper

import com.belascore.game.data.db.model.GameEntity
import com.belascore.game.data.db.model.ScoreEntity
import com.belascore.game.data.db.model.TeamEntity
import com.belascore.game.domain.model.DeclarationType
import com.belascore.game.domain.model.Game
import com.belascore.game.domain.model.Score
import com.belascore.game.domain.model.SpecialPoints
import com.belascore.game.domain.model.Team
import com.belascore.newGame.ui.PlayerCount

internal class DbMapperImpl : DbMapper {
    override fun toGameEntity(game: Game) =
        GameEntity(
            id = game.id,
            winningScore = game.winningScore,
            numberOfPlayers = game.playerCount.count,
            isInProgress = game.isInProgress
        )

    override fun toTeamEntity(team: Team) =
        TeamEntity(
            id = team.id,
            name = team.name
        )

    override fun toScoreEntity(
        gameId: Long,
        teamId: Long,
        roundNumber: Int,
        baseScore: Int,
        declarations: Map<DeclarationType, Int>,
        specialPoints: Set<SpecialPoints>
    ) = ScoreEntity(
        gameId = gameId,
        teamId = teamId,
        roundNumber = roundNumber,
        baseScore = baseScore,
        declarations = declarations,
        specialPoints = specialPoints,
        totalScore = baseScore +
                declarations
                    .map { (declaration, count) -> declaration.points * count }
                    .sum() + specialPoints.sumOf(SpecialPoints::points)
    )

    override fun fromGameEntity(entity: GameEntity) = Game(
        id = entity.id,
        winningScore = entity.winningScore,
        playerCount = PlayerCount.fromCount(entity.numberOfPlayers),
        isInProgress = entity.isInProgress
    )

    override fun fromTeamEntity(entity: TeamEntity) =
        Team(
            id = entity.id,
            name = entity.name
        )

    override fun fromScoreEntity(entity: ScoreEntity) = entity.run {
        Score(
            gameId = gameId,
            teamId = teamId,
            roundNumber = roundNumber,
            baseScore = baseScore,
            totalScore = totalScore,
            declarations = declarations,
            specialPoints = specialPoints
        )
    }
}
