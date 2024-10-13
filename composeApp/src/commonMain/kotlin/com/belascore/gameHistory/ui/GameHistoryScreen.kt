package com.belascore.gameHistory.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import belascore.composeapp.generated.resources.Res
import belascore.composeapp.generated.resources.game_history
import com.belascore.coreUi.common.BackIcon
import com.belascore.coreUi.common.Screen
import com.belascore.coreUi.common.SwipeableItemWithAction
import com.belascore.coreUi.common.TopBar
import com.belascore.gameHistory.ui.components.ActionItem
import com.belascore.gameHistory.ui.components.GameItem
import org.jetbrains.compose.resources.stringResource

@Composable
fun GameHistoryScreen(
    viewModel: GameHistoryViewModel,
    onGameClick: (GameSummaryUiState) -> Unit,
    onBackClick: () -> Unit
) = Screen {
    val gameHistoryUiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(Res.string.game_history),
                navigationIcon = { BackIcon(onBackClick = onBackClick) }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(gameHistoryUiState.games.size) { index ->
                val game = gameHistoryUiState.games[index]

                SwipeableItemWithAction(
                    isRevealed = false,
                    actions = {
                        ActionItem(
                            onClick = { viewModel.deleteGame(game.id) },
                            icon = Icons.Default.Delete,
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(all = 8.dp)
                        )
                    }
                ) {
                    GameItem(
                        game = game,
                        modifier = Modifier
                            .clickable { onGameClick(game) }
                            .padding(all = 16.dp)
                    )
                }

                if (index < gameHistoryUiState.games.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}
