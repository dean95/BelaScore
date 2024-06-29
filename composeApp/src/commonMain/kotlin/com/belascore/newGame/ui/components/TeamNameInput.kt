package com.belascore.newGame.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import belascore.composeapp.generated.resources.Res
import belascore.composeapp.generated.resources.team_name
import org.jetbrains.compose.resources.stringResource

@Composable
fun TeamNameInput(
    teamName: String,
    onTeamNameChange: (String) -> Unit,
    index: Int,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = teamName,
        onValueChange = {
            onTeamNameChange(it)
        },
        placeholder = {
            Text(
                text = stringResource(Res.string.team_name, index + 1),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
            )
        },
        modifier = modifier.padding(8.dp),
    )
}
