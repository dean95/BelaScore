package com.belascore.game.data.repository

import com.belascore.game.data.db.dao.GameAndTeamCompositeDao
import com.belascore.game.data.db.dao.GameDao
import com.belascore.game.data.db.dao.ScoreDao
import com.belascore.game.data.db.dao.TeamDao
import com.belascore.game.data.db.mapper.DbMapper
import com.belascore.game.data.db.model.GameEntity
import com.belascore.game.data.db.model.ScoreEntity
import com.belascore.game.data.db.model.TeamEntity
import com.belascore.game.domain.model.Game
import com.belascore.game.domain.model.Team
import com.belascore.game.domain.repository.GameRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope

internal class GameRepositoryImpl(
    private val gameDao: GameDao,
    private val scoreDao: ScoreDao,
    private val teamDao: TeamDao,
    private val gameAndTeamCompositeDao: GameAndTeamCompositeDao,
    private val dbMapper: DbMapper
) : GameRepository {

    override suspend fun insertGameWithTeams(winningScore: Int, teamNames: List<String>): Long =
        gameAndTeamCompositeDao
            .insertGameWithTeams(
                game = GameEntity(
                    winningScore = winningScore,
                    isInProgress = true
                ),
                teams = teamNames.map { TeamEntity(name = it) }
            )

    override fun observeWinningTeams(gameId: Long): Flow<List<Team>> =
        combine(
            gameDao.observeWinningScoreForGame(gameId),
            scoreDao.observeScoresByGame(gameId)
        ) { winningScore, scoresForGame ->
            scoresForGame
                .groupBy(ScoreEntity::teamId)
                .filter { (_, scoresForTeam) ->
                    scoresForTeam.sumOf(ScoreEntity::score) >= winningScore
                }.map { (teamId, _) ->
                    val winningTeam = teamDao.observeTeamById(teamId).first()
                    dbMapper.fromTeamEntity(winningTeam)
                }
        }

    override suspend fun endGame(gameId: Long) = gameDao.endGame(gameId)

    override suspend fun deleteGame(gameId: Long) {
        supervisorScope {
            awaitAll(
                async { gameDao.deleteGame(gameId) },
                async { gameDao.deleteGameTeamCrossRefsForGame(gameId) },
                async { scoreDao.deleteScoresForGame(gameId) }
            )
        }
    }

    override fun observeActiveGame(): Flow<Game?> =
        gameDao
            .observeActiveGame()
            .map { it?.let(dbMapper::fromGameEntity) }
}
