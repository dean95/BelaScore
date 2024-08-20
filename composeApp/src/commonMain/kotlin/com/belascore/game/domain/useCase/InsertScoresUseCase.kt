package com.belascore.game.domain.useCase

import com.belascore.game.domain.model.DeclarationType
import com.belascore.game.domain.model.SpecialPoints
import com.belascore.game.domain.repository.ScoreRepository

class InsertScoresUseCase(
    private val scoreRepository: ScoreRepository
) {

    suspend operator fun invoke(params: List<Param>) = scoreRepository.insertScores(params)

    class Param(
        val gameId: Long,
        val teamId: Long,
        val roundNumber: Int,
        val score: Int,
        val declarations: Map<DeclarationType, Int>,
        val specialPoints: Set<SpecialPoints>
    )
}
