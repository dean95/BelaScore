package com.belascore.coreUi.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import belascore.composeapp.generated.resources.Res
import belascore.composeapp.generated.resources.cd_close
import org.jetbrains.compose.resources.stringResource

@Composable
fun CloseIcon(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onCloseClick
    ) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(Res.string.cd_close)
        )
    }
}
