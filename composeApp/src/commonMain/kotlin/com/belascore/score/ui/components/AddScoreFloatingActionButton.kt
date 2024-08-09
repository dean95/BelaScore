package com.belascore.score.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults.containerColor
import androidx.compose.material3.Icon
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import belascore.composeapp.generated.resources.Res
import belascore.composeapp.generated.resources.cd_add_scores
import org.jetbrains.compose.resources.stringResource

@Composable
fun AddScoreFloatingActionButton(
    isGameInProgress: Boolean,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = if (isGameInProgress) containerColor else Color.Gray,
        contentColor = if (isGameInProgress) contentColorFor(containerColor) else Color.DarkGray
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(Res.string.cd_add_scores)
        )
    }
}
