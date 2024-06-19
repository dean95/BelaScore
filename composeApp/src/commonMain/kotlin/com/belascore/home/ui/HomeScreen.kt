package com.belascore.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNewGameClick: () -> Unit,
    onGameHistoryClick: () -> Unit,
) {
    Surface(
        modifier =
            modifier
                .fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
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
                Text(text = "New Game")
            }
            Button(
                onClick = onGameHistoryClick,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
            ) {
                Text(text = "Game History")
            }
        }
    }
}
