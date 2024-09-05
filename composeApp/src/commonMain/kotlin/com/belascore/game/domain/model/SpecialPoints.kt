package com.belascore.game.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class SpecialPoints(val points: Int) {
    BELA(20),
    STIGLJA(90)
}
