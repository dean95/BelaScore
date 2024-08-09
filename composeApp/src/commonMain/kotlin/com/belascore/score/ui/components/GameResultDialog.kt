package com.belascore.score.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import belascore.composeapp.generated.resources.Res
import belascore.composeapp.generated.resources.got_it
import belascore.composeapp.generated.resources.winner
import com.belascore.score.ui.TeamUiState
import org.jetbrains.compose.resources.stringResource

@Composable
fun GameResultDialog(
    winningTeams: List<TeamUiState>,
    onConfirmation: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {
            /* no-op */
        },
        confirmButton = {
            TextButton(
                onClick = onConfirmation
            ) {
                Text(stringResource(Res.string.got_it))
            }
        },
        icon = {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = null
            )
        },
        title = {
            Text(text = stringResource(Res.string.winner))
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                winningTeams.forEach { team ->
                    Text(text = team.name)
                }
            }
        },
        modifier = modifier
    )
}
