package com.belascore.score.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import belascore.composeapp.generated.resources.Res
import belascore.composeapp.generated.resources.confirm
import belascore.composeapp.generated.resources.team_score
import com.belascore.score.ui.TeamUiState
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreInputBottomSheet(
    sheetState: SheetState,
    teams: List<TeamUiState>,
    onDismissRequest: () -> Unit,
    onConfirm: (Map<Long, Int>) -> Unit,
    modifier: Modifier = Modifier
) {
    var scoresInput by remember { mutableStateOf(teams.associate { it.id to "" }) }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            teams.forEach { team ->
                OutlinedTextField(
                    value = scoresInput.getValue(team.id),
                    onValueChange = { newValue ->
                        scoresInput = scoresInput.toMutableMap().apply { put(team.id, newValue) }
                    },
                    label = { Text(stringResource(Res.string.team_score, team.name)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(
                onClick = {
                    val scoresMap = scoresInput.mapValues { (_, scoreString) -> scoreString.toInt() }
                    onConfirm(scoresMap)
                }
            ) {
                Text(text = stringResource(Res.string.confirm))
            }
        }
    }
}
