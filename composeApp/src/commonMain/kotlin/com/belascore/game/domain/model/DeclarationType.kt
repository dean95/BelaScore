package com.belascore.game.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class DeclarationType(val points: Int) {
    TWENTY(20),
    FIFTY(50),
    HUNDRED(100),
    ONE_FIFTY(150),
    TWO_HUNDRED(200)
}
