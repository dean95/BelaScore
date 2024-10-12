package com.belascore.score.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow

private const val EMPTY_SCORE_DISPLAY = "—"

@Composable
fun ScoreItem(score: Int) {
    Text(
        text = if (score == 0) EMPTY_SCORE_DISPLAY else score.toString(),
        style = MaterialTheme.typography.headlineSmall,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}
