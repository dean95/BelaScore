package com.belascore.score.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
    onBackClick: () -> Unit
) = Screen {
    val scoreUiState by viewModel.uiState.collectAsStateWithLifecycle()
    var currentDialog: DialogState by rememberSaveable { mutableStateOf(DialogState.None) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val isGameInProgress = scoreUiState.winningTeams.isEmpty()


    OnBackPressed(enabled = isGameInProgress) {
        currentDialog = DialogState.QuitConfirmation
    }

    // FIXME This will showing GameResult dialog when navigating from history.
    if (scoreUiState.winningTeams.isNotEmpty()) {
        currentDialog = DialogState.GameResult
    }

    Scaffold(
        topBar = {
            ScoreScreenTopBar(
                isGameInProgress = isGameInProgress,
                onBackClick = onBackClick
            ) {
                currentDialog = it
            }
        },
        floatingActionButton = {
            AddScoreFloatingActionButton(
                isGameInProgress = isGameInProgress
            ) {
                if (isGameInProgress) {
                    currentDialog = DialogState.ScoreInput
                }
            }
        }
    ) { paddingValues ->
        ScoreContent(
            scoreUiState = scoreUiState,
            modifier = Modifier
                .fillMaxSize()
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
        LaunchedEffect(Unit) { onBackClick() }
    }
}
