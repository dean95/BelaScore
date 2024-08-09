package com.belascore.score.ui.components

import androidx.compose.runtime.Composable
import com.belascore.coreUi.common.CloseIcon
import com.belascore.coreUi.common.TopBar
import com.belascore.score.ui.DialogState

@Composable
fun ScoreScreenTopBar(
    isGameInProgress: Boolean,
    onCloseClick: () -> Unit,
    onShowQuitConfirmation: (DialogState) -> Unit
) {
    TopBar(
        navigationIcon = {
            CloseIcon(
                onCloseClick = {
                    if (isGameInProgress) {
                        onShowQuitConfirmation(DialogState.QuitConfirmation)
                    } else {
                        onCloseClick()
                    }
                }
            )
        }
    )
}
