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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import belascore.composeapp.generated.resources.Res
import belascore.composeapp.generated.resources.new_game
import belascore.composeapp.generated.resources.number_of_players
import belascore.composeapp.generated.resources.start_game
import belascore.composeapp.generated.resources.winning_score
import com.belascore.coreUi.Screen
import com.belascore.newGame.ui.components.SelectableButton
import com.belascore.newGame.ui.components.TeamNameInput
import org.jetbrains.compose.resources.stringResource

@Composable
fun NewGameScreen(viewModel: NewGameViewModel = viewModel { NewGameViewModel() }) =
    Screen {
        val newGameUiState = viewModel.uiState.collectAsState().value

        val teams =
            rememberSaveable(newGameUiState.gameOptions.playerCount.count) {
                MutableList(newGameUiState.gameOptions.playerCount.count) { "" }.toMutableStateList()
            }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(Res.string.new_game),
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp),
            )

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
                onClick = { /*TODO()*/ },
            ) {
                Text(text = stringResource(Res.string.start_game))
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
