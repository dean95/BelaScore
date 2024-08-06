package com.belascore.score.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults.containerColor
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import belascore.composeapp.generated.resources.Res
import belascore.composeapp.generated.resources.cd_add_scores
import com.belascore.coreUi.common.BackIcon
import com.belascore.coreUi.common.Screen
import com.belascore.coreUi.common.TopBar
import com.belascore.coreUi.navigation.OnBackPressed
import com.belascore.score.ui.components.GameResultDialog
import com.belascore.score.ui.components.QuitGameConfirmationDialog
import com.belascore.score.ui.components.ScoreInputBottomSheet
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreScreen(
    viewModel: ScoreViewModel,
    onBackClick: () -> Unit
) = Screen {
    val scoreUiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

    val isGameInProgress = scoreUiState.winningTeams.isEmpty()

    var showQuitConfirmation by rememberSaveable { mutableStateOf(false) }

    OnBackPressed(isGameInProgress) {
        showQuitConfirmation = true
    }

    Scaffold(
        topBar = {
            TopBar(
                navigationIcon = {
                    BackIcon(
                        onBackClick = {
                            if (isGameInProgress) {
                                showQuitConfirmation = true
                            } else {
                                onBackClick()
                            }
                        }
                    )
                })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (isGameInProgress) {
                        showBottomSheet = true
                    }
                },
                containerColor = if (isGameInProgress) containerColor else Color.Gray,
                contentColor = if (isGameInProgress) contentColorFor(containerColor) else Color.DarkGray
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(Res.string.cd_add_scores)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                scoreUiState.teams.forEach { team ->
                    Text(
                        text = team.name,
                        fontSize = 20.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            scoreUiState.rounds.forEach { round ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    scoreUiState.teams.forEach { team ->
                        Text(
                            text = round.scores.getValue(team.id).toString(),
                            fontSize = 16.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

        if (showBottomSheet) {
            ScoreInputBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    scope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                },
                onConfirm = { teamScores, teamDeclarations, teamSpecialPoints ->
                    scope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            viewModel.updateScores(
                                teamScores = teamScores,
                                teamDeclarations = teamDeclarations,
                                teamSpecialPoints = teamSpecialPoints,
                                roundNumber = scoreUiState.rounds.size + 1
                            )
                            showBottomSheet = false
                        }
                    }
                },
                teams = scoreUiState.teams,
                modifier = Modifier.fillMaxHeight()
            )
        }

        if (!isGameInProgress) {
            var showDialog by rememberSaveable { mutableStateOf(true) }
            if (showDialog) {
                GameResultDialog(
                    winningTeams = scoreUiState.winningTeams,
                    onConfirmation = { showDialog = false }
                )
            }
        }

        if (showQuitConfirmation) {
            QuitGameConfirmationDialog(
                onConfirmQuit = {
                    viewModel.quitGame()
                    showQuitConfirmation = false
                    onBackClick()
                },
                onDismiss = { showQuitConfirmation = false }
            )
        }
    }
}
