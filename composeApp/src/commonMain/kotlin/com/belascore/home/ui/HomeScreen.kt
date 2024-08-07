package com.belascore.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import belascore.composeapp.generated.resources.Res
import belascore.composeapp.generated.resources.game_history
import belascore.composeapp.generated.resources.new_game
import com.belascore.coreUi.common.Screen
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNewGameClick: () -> Unit,
    onGameHistoryClick: () -> Unit,
    onGameResumed: (Long) -> Unit
) = Screen {
    val homeUiState by viewModel.uiState.collectAsStateWithLifecycle()

    val activeGameId = homeUiState.activeGameId

    if (activeGameId != null) {
        LaunchedEffect(activeGameId) {
            onGameResumed(activeGameId)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = onNewGameClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = stringResource(Res.string.new_game))
            }
            Button(
                onClick = onGameHistoryClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Text(text = stringResource(Res.string.game_history))
            }
        }
    }
}
