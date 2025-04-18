package com.belascore.score.ui

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.belascore.coreUi.common.Screen
import com.belascore.coreUi.navigation.OnBackPressed
import com.belascore.score.ui.components.AddScoreFloatingActionButton
import com.belascore.score.ui.components.ScoreContent
import com.belascore.score.ui.components.ScoreScreenDialogs
import com.belascore.score.ui.components.ScoreScreenTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreScreen(
    viewModel: ScoreViewModel,
    showGameResultDialog: Boolean,
    onCloseClick: () -> Unit
) = Screen {
    val scoreUiState by viewModel.uiState.collectAsStateWithLifecycle()
    var currentDialog: DialogState by remember { mutableStateOf(DialogState.None) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val isGameInProgress = scoreUiState.winningTeams.isEmpty()


    OnBackPressed(enabled = isGameInProgress) {
        currentDialog = DialogState.QuitConfirmation
    }

    if (showGameResultDialog && !isGameInProgress) {
        currentDialog = DialogState.GameResult
    }

    Scaffold(
        topBar = {
            ScoreScreenTopBar(
                isGameInProgress = isGameInProgress,
                onCloseClick = onCloseClick
            ) {
                currentDialog = it
            }
        },
        floatingActionButton = {
            if (isGameInProgress) {
                AddScoreFloatingActionButton {
                    currentDialog = DialogState.ScoreInput()
                }
            }
        }
    ) { paddingValues ->
        ScoreContent(
            scoreUiState = scoreUiState,
            onEditScoreClick = {
                currentDialog = DialogState.ScoreInput(it.roundScores)
            },
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        )
    }

    ScoreScreenDialogs(
        currentDialog = currentDialog,
        sheetState = sheetState,
        scoreUiState = scoreUiState,
        onDismissDialog = { currentDialog = DialogState.None },
        onUpdateScores = viewModel::updateScores,
        onQuitGame = viewModel::quitGame
    )

    if (scoreUiState.quitGame) {
        LaunchedEffect(Unit) { onCloseClick() }
    }
}
