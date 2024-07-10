package com.belascore.game.domain.repository

interface GameRepository {

    suspend fun insertGame(winningScore: Int): Long
}