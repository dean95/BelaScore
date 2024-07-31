package com.belascore.score.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import belascore.composeapp.generated.resources.Res
import belascore.composeapp.generated.resources.cancel
import belascore.composeapp.generated.resources.confirm
import belascore.composeapp.generated.resources.team_score
import com.belascore.game.domain.model.DeclarationType
import com.belascore.game.domain.model.MAX_SCORE_WITHOUT_SPECIAL_POINTS
import com.belascore.game.domain.model.SpecialPoints
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
    var scoresInput by remember { mutableStateOf(teams.associate { it.id to 0 }) }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        modifier = modifier
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                teams.forEach { team ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ScoreInput(
                            value = scoresInput.getValue(team.id),
                            label = stringResource(Res.string.team_score, team.name),
                            onValueChange = { newValue ->
                                val newScore = newValue.toInt()

                                if (scoresInput.size == 2) {
                                    val otherTeamId = teams.find {
                                        it.id != team.id
                                    }?.id ?: error("Team not found")

                                    val remainingScore = MAX_SCORE_WITHOUT_SPECIAL_POINTS - newScore

                                    scoresInput = scoresInput.toMutableMap().apply {
                                        put(team.id, newScore)
                                        put(otherTeamId, remainingScore)
                                    }
                                } else {
                                    scoresInput = scoresInput.toMutableMap().apply {
                                        put(team.id, newScore)
                                    }
                                }
                            }
                        )

                        DeclarationStepper(
                            declarationType = DeclarationType.TWENTY,
                            onCountChange = {
                                /* no-op */
                            }
                        )

                        DeclarationStepper(
                            declarationType = DeclarationType.FIFTY,
                            onCountChange = {
                                /* no-op */
                            }
                        )

                        DeclarationStepper(
                            declarationType = DeclarationType.HUNDRED,
                            onCountChange = {
                                /* no-op */
                            }
                        )

                        DeclarationStepper(
                            declarationType = DeclarationType.ONE_FIFTY,
                            onCountChange = {
                                /* no-op */
                            }
                        )

                        DeclarationStepper(
                            declarationType = DeclarationType.TWO_HUNDRED,
                            onCountChange = {
                                /* no-op */
                            }
                        )

                        SpecialPointsSwitch(
                            specialPoints = SpecialPoints.BELA,
                            onCheckedChange = {
                                /* no-op */
                            }
                        )

                        SpecialPointsSwitch(
                            specialPoints = SpecialPoints.STIGLJA,
                            onCheckedChange = {
                                /* no-op */
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row {
                FilledTonalButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text(text = stringResource(Res.string.cancel))
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { onConfirm(scoresInput) }
                ) {
                    Text(text = stringResource(Res.string.confirm))
                }
            }
        }
    }
}
