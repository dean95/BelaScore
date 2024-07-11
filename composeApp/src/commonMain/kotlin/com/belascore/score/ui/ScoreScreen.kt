package com.belascore.score.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.belascore.coreUi.common.BackIcon
import com.belascore.coreUi.common.Screen
import com.belascore.coreUi.common.TopBar

@Composable
fun ScoreScreen(
    viewModel: ScoreViewModel, onBackClick: () -> Unit
) = Screen {
    val scoreUiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopBar(
                navigationIcon = { BackIcon(onBackClick = onBackClick) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                // teams.forEach { team ->
                //     LazyColumn(
                //         horizontalAlignment = Alignment.CenterHorizontally,
                //     ) {
                //         items(rounds) { round ->
                //             Text(
                //                 text = "${round.roundNumber}: ${round.scores[team.id]}",
                //                 fontSize = 16.sp,
                //                 maxLines = 1,
                //                 overflow = TextOverflow.Ellipsis,
                //             )
                //         }
                //     }
                // }
            }
        }
    }
}
