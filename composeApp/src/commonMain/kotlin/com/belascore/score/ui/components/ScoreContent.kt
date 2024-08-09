package com.belascore.score.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.belascore.score.ui.ScoreUiState

@Composable
fun ScoreContent(
    scoreUiState: ScoreUiState,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        scoreUiState.teams.forEachIndexed { index, team ->
            TeamScoreColumn(
                team = team,
                rounds = scoreUiState.rounds
            )

            if (index < scoreUiState.teams.lastIndex) {
                VerticalDivider(modifier = Modifier.fillMaxHeight())
            }
        }
    }
}
