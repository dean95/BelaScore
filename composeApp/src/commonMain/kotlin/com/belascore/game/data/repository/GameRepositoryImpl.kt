package com.belascore.game.data.repository

import com.belascore.game.data.db.dao.GameDao
import com.belascore.game.data.db.mapper.DbMapper
import com.belascore.game.data.db.model.GameEntity
import com.belascore.game.data.db.model.ScoreEntity
import com.belascore.game.data.db.model.TeamEntity
import com.belascore.game.domain.model.Game
import com.belascore.game.domain.model.Team
import com.belascore.game.domain.repository.GameRepository
import com.belascore.newGame.ui.PlayerCount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class GameRepositoryImpl(
    private val gameDao: GameDao,
    private val dbMapper: DbMapper
) : GameRepository {

    override suspend fun insertGameWithTeams(
        winningScore: Int,
        playerCount: PlayerCount,
        teamNames: List<String>
    ): Long =
        gameDao
            .insertGameWithTeams(
                game = GameEntity(
                    winningScore = winningScore,
                    numberOfPlayers = playerCount.count,
                    isInProgress = true
                ),
                teams = teamNames.map { TeamEntity(name = it) }
            )

    override fun observeWinningTeams(gameId: Long): Flow<List<Team>> =
        combine(
            gameDao.observeWinningScoreForGame(gameId),
            gameDao.observeScoresByGame(gameId)
        ) { winningScore, scoresForGame ->
            scoresForGame
                .groupBy(ScoreEntity::teamId)
                .filter { (_, scoresForTeam) ->
                    scoresForTeam.sumOf(ScoreEntity::totalScore) >= winningScore
                }
                .let { qualifiedTeams ->

                    val maxScore = qualifiedTeams.maxOfOrNull { (_, scoresForTeam) ->
                        scoresForTeam.sumOf(ScoreEntity::totalScore)
                    }

                    qualifiedTeams.filterValues { scoresForTeam ->
                        scoresForTeam.sumOf(ScoreEntity::totalScore) == maxScore
                    }
                }
                .map { (teamId, _) ->
                    val winningTeam = gameDao.observeTeamById(teamId).first()
                    dbMapper.fromTeamEntity(winningTeam)
                }
        }

    override suspend fun endGame(gameId: Long) = gameDao.endGame(gameId = gameId)

    override suspend fun deleteGame(gameId: Long) =
        gameDao.deleteGameWithScores(gameId = gameId)

    override fun observeActiveGame(): Flow<Game?> =
        gameDao
            .observeActiveGame()
            .map { it?.let(dbMapper::fromGameEntity) }

    override fun observePastGames(): Flow<List<Game>> =
        gameDao
            .observePastGames()
            .map { it.map(dbMapper::fromGameEntity) }

    override fun observeGameById(gameId: Long): Flow<Game> =
        gameDao
            .observeGameById(gameId = gameId)
            .filterNotNull()
            .map(dbMapper::fromGameEntity)
}
