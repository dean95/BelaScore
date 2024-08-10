package com.belascore.score.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.belascore.score.ui.RoundItemUiState
import com.belascore.score.ui.TeamUiState

@Composable
fun RowScope.TeamScoreColumn(
    team: TeamUiState,
    rounds: List<RoundItemUiState>
) {
    Column(
        modifier = Modifier.weight(weight = 1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = team.name,
            style = MaterialTheme.typography.headlineLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        rounds.forEach { round ->
            ScoreItem(score = round.scores.getValue(team.id))
        }

        if (rounds.size > 1) {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            ScoreItem(score = team.totalScore)
        }
    }
}
