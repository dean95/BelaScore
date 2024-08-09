package com.belascore.score.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RowScope.TeamScoreColumn(
    team: TeamUiState,
    rounds: List<RoundItemUiState>,
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.weight(weight = 1f)
    ) {
        stickyHeader {
            Text(
                text = team.name,
                style = MaterialTheme.typography.headlineLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        item {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )
        }

        items(rounds) { round ->
            ScoreItem(score = round.scores.getValue(team.id))
        }

        if (rounds.size > 1) {
            item {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )
            }

            item {
                ScoreItem(score = team.totalScore)
            }
        }
    }
}
