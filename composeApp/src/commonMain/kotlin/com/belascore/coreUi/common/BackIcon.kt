package com.belascore.coreUi.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import belascore.composeapp.generated.resources.Res
import belascore.composeapp.generated.resources.cd_back
import org.jetbrains.compose.resources.stringResource

@Composable
fun BackIcon(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onBackClick
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(Res.string.cd_back)
        )
    }
}