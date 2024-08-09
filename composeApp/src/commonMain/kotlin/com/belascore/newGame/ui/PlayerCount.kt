package com.belascore.newGame.ui

enum class PlayerCount(
    val count: Int,
    val defaultScore: Int,
) {
    TWO(2, 501),
    THREE(3, 701),
    FOUR(4, 1001);

    companion object {
        fun fromCount(count: Int): PlayerCount =
            entries.find { it.count == count } ?: error("Invalid player count: $count")
    }
}
