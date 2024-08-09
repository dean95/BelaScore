package com.belascore.score.ui.components

import androidx.compose.runtime.Composable
import com.belascore.coreUi.common.BackIcon
import com.belascore.coreUi.common.TopBar
import com.belascore.score.ui.DialogState

@Composable
fun ScoreScreenTopBar(
    isGameInProgress: Boolean,
    onBackClick: () -> Unit,
    onShowQuitConfirmation: (DialogState) -> Unit
) {
    TopBar(
        navigationIcon = {
            BackIcon(
                onBackClick = {
                    if (isGameInProgress) {
                        onShowQuitConfirmation(DialogState.QuitConfirmation)
                    } else {
                        onBackClick()
                    }
                }
            )
        }
    )
}
