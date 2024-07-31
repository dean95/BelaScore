package com.belascore.newGame.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import belascore.composeapp.generated.resources.Res
import belascore.composeapp.generated.resources.new_game
import belascore.composeapp.generated.resources.number_of_players
import belascore.composeapp.generated.resources.start_game
import belascore.composeapp.generated.resources.winning_score
import com.belascore.coreUi.common.BackIcon
import com.belascore.coreUi.common.Screen
import com.belascore.coreUi.common.TopBar
import com.belascore.newGame.ui.components.SelectableButton
import com.belascore.newGame.ui.components.TeamNameInput
import org.jetbrains.compose.resources.stringResource

@Composable
fun NewGameScreen(
    viewModel: NewGameViewModel,
    onStartGameClick: (Long) -> Unit,
    onBackClick: () -> Unit
) = Screen {
    val newGameUiState by viewModel.uiState.collectAsStateWithLifecycle()

    val teamCount = newGameUiState.gameOptions.teamCount
    val teams = remember(teamCount) {
        MutableList(teamCount) { "" }.toMutableStateList()
    }

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(Res.string.new_game),
                navigationIcon = { BackIcon(onBackClick = onBackClick) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            WinningScoreSelector(
                playerCount = newGameUiState.gameOptions.playerCount.count,
                teams = teams,
                onTeamNameChange = { index, teamName -> teams[index] = teamName },
            )

            Spacer(modifier = Modifier.height(16.dp))

            PlayerCountSelector(
                count = newGameUiState.gameOptions.playerCount.count,
                winningScore = newGameUiState.gameOptions.winningScore,
                onUpdatePlayerCount = viewModel::updatePlayerCount,
                onUpdateWinningScore = viewModel::updateWinningScore,
            )

            Button(
                onClick = {
                    viewModel.createNewGame(
                        winningScore = newGameUiState.gameOptions.winningScore,
                        teamNames = teams,
                        onStartGameClick = onStartGameClick
                    )
                },
            ) {
                Text(text = stringResource(Res.string.start_game))
            }
        }
    }
}

@Composable
fun WinningScoreSelector(
    playerCount: Int,
    teams: List<String>,
    onTeamNameChange: (Int, String) -> Unit,
) {
    repeat(playerCount.takeIf { it == PlayerCount.THREE.count } ?: 2) { index ->
        TeamNameInput(
            teamName = teams[index],
            onTeamNameChange = { onTeamNameChange(index, it) },
            index = index,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
fun PlayerCountSelector(
    count: Int,
    winningScore: Int,
    onUpdatePlayerCount: (PlayerCount) -> Unit,
    onUpdateWinningScore: (Int) -> Unit,
) {
    Text(text = stringResource(Res.string.number_of_players))

    Spacer(modifier = Modifier.width(8.dp))

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth(),
    ) {
        PlayerCount.entries.forEachIndexed { index, playerCount ->
            SelectableButton(
                text = playerCount.count.toString(),
                isSelected = count == playerCount.count,
                onClick = { onUpdatePlayerCount(playerCount) },
            )

            if (index < PlayerCount.entries.lastIndex) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = stringResource(Res.string.winning_score),
        fontSize = 16.sp,
        modifier = Modifier.padding(bottom = 8.dp),
    )

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth(),
    ) {
        PlayerCount.entries.forEachIndexed { index, playerCount ->
            SelectableButton(
                text = playerCount.defaultScore.toString(),
                isSelected = winningScore == playerCount.defaultScore,
                onClick = { onUpdateWinningScore(it.toInt()) },
            )

            if (index < PlayerCount.entries.lastIndex) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }

    Spacer(modifier = Modifier.height(32.dp))
}
