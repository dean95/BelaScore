package com.belascore.score.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import belascore.composeapp.generated.resources.Res
import belascore.composeapp.generated.resources.keep_playing
import belascore.composeapp.generated.resources.quit
import belascore.composeapp.generated.resources.quit_game_message
import belascore.composeapp.generated.resources.quit_game_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun QuitGameConfirmationDialog(
    onConfirmQuit: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirmQuit) {
                Text(stringResource(Res.string.quit))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(Res.string.keep_playing))
            }
        },
        icon = {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = null
            )
        },
        title = {
            Text(stringResource(Res.string.quit_game_title))
        },
        text = {
            Text(stringResource(Res.string.quit_game_message))
        },
        modifier = modifier
    )
}
