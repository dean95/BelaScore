package com.belascore.score.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import belascore.composeapp.generated.resources.Res
import belascore.composeapp.generated.resources.cd_add_scores
import com.belascore.coreUi.common.BackIcon
import com.belascore.coreUi.common.Screen
import com.belascore.coreUi.common.TopBar
import com.belascore.score.ui.components.ScoreInputDialog
import org.jetbrains.compose.resources.stringResource

@Composable
fun ScoreScreen(
    viewModel: ScoreViewModel,
    onBackClick: () -> Unit
) = Screen {
    val scoreUiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(navigationIcon = { BackIcon(onBackClick = onBackClick) })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true }
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

        if (showDialog) {
            ScoreInputDialog(
                teams = scoreUiState.teams,
                onDismissRequest = { showDialog = false },
                onConfirm = { scoresMap ->
                    viewModel.updateScores(scoresMap, scoreUiState.rounds.size + 1)
                    showDialog = false
                }
            )
        }
    }
}


