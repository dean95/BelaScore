package com.belascore.gameHistory.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.belascore.gameHistory.ui.GameSummaryUiState

@Composable
fun GameItem(
    game: GameSummaryUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        game.teams.forEach { team ->
            Row {
                Text(
                    text = team.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(text = team.score.toString())
            }
        }
    }
}
