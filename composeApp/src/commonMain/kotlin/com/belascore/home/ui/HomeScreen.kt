package com.belascore.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import belascore.composeapp.generated.resources.Res
import belascore.composeapp.generated.resources.game_history
import belascore.composeapp.generated.resources.new_game
import com.belascore.coreUi.Screen
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen(
    onNewGameClick: () -> Unit,
    onGameHistoryClick: () -> Unit,
) = Screen {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = onNewGameClick,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
        ) {
            Text(text = stringResource(Res.string.new_game))
        }
        Button(
            onClick = onGameHistoryClick,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
        ) {
            Text(text = stringResource(Res.string.game_history))
        }
    }
}
