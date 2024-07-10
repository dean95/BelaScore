package com.belascore.game.domain.repository

interface TeamRepository {

    suspend fun insertTeam(name: String): Long
}