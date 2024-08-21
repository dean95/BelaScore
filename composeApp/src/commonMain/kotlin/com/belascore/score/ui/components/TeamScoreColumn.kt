package com.belascore.score.ui.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import belascore.composeapp.generated.resources.Res
import belascore.composeapp.generated.resources.edit_score
import com.belascore.score.ui.RoundItemUiState
import com.belascore.score.ui.TeamUiState
import org.jetbrains.compose.resources.stringResource

@Composable
fun RowScope.TeamScoreColumn(
    team: TeamUiState,
    rounds: List<RoundItemUiState>,
    isGameInProgress: Boolean,
    onEditScoreClick: (RoundItemUiState) -> Unit
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
            var showMenu by rememberSaveable { mutableStateOf(false) }

            Box(
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                showMenu = isGameInProgress
                            }
                        )
                    }
            ) {
                ScoreItem(
                    score = round.roundScores.scores.getValue(team.id).totalScore
                )

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(Res.string.edit_score),
                                fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                            )
                        },
                        onClick = {
                            onEditScoreClick(round)
                            showMenu = false
                        }
                    )
                }
            }
        }

        if (rounds.size > 1) {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            ScoreItem(
                score = team.totalScore
            )
        }
    }
}
