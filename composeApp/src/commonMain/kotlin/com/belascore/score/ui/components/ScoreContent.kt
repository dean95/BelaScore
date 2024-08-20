package com.belascore.score.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.belascore.score.ui.RoundItemUiState
import com.belascore.score.ui.ScoreUiState

@Composable
fun ScoreContent(
    scoreUiState: ScoreUiState,
    onEditScoreClick: (RoundItemUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        scoreUiState.teams.forEach { team ->
            TeamScoreColumn(
                team = team,
                rounds = scoreUiState.rounds,
                onEditScoreClick = onEditScoreClick
            )
        }
    }
}
