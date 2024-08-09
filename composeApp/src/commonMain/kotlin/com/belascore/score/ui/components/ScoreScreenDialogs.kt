package com.belascore.score.ui.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.belascore.game.domain.model.DeclarationType
import com.belascore.game.domain.model.SpecialPoints
import com.belascore.score.ui.DialogState
import com.belascore.score.ui.ScoreUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreScreenDialogs(
    currentDialog: DialogState,
    sheetState: SheetState,
    scoreUiState: ScoreUiState,
    onDismissDialog: () -> Unit,
    onUpdateScores: (
        Map<Long, Int>,
        Map<Long, Map<DeclarationType, Int>>,
        Map<Long, Set<SpecialPoints>>,
        Int
    ) -> Unit,
    onQuitGame: () -> Unit
) {

    val scope = rememberCoroutineScope()
    when (currentDialog) {
        DialogState.ScoreInput -> {
            ScoreInputBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    scope.launch { sheetState.hide() }
                        .invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onDismissDialog()
                            }
                        }
                },
                onConfirm = { teamScores, teamDeclarations, teamSpecialPoints ->
                    scope.launch { sheetState.hide() }
                        .invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onUpdateScores(
                                    teamScores,
                                    teamDeclarations,
                                    teamSpecialPoints,
                                    scoreUiState.rounds.size + 1
                                )
                                onDismissDialog()
                            }
                        }
                },
                teams = scoreUiState.teams,
                playerCount = scoreUiState.playerCount,
                modifier = Modifier.fillMaxHeight()
            )
        }

        DialogState.GameResult -> {
            var showDialog by rememberSaveable { mutableStateOf(true) }
            if (showDialog) {
                GameResultDialog(
                    winningTeams = scoreUiState.winningTeams,
                    onConfirmation = {
                        onDismissDialog()
                        showDialog = false
                    }
                )
            }
        }

        DialogState.QuitConfirmation -> {
            QuitGameConfirmationDialog(
                onConfirmQuit = {
                    onQuitGame()
                    onDismissDialog()
                },
                onDismiss = onDismissDialog
            )
        }

        DialogState.None -> {
            /* no-op */
        }
    }
}
