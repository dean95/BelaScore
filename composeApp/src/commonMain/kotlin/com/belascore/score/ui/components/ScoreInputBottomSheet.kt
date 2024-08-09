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
import com.belascore.game.domain.model.TOTAL_SCORE_WITHOUT_SPECIAL_POINTS
import com.belascore.game.domain.model.SpecialPoints
import com.belascore.newGame.ui.PlayerCount
import com.belascore.score.ui.TeamUiState
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreInputBottomSheet(
    sheetState: SheetState,
    teams: List<TeamUiState>,
    playerCount: PlayerCount,
    onDismissRequest: () -> Unit,
    onConfirm: (
        Map<Long, Int>,
        Map<Long, Map<DeclarationType, Int>>,
        Map<Long, Set<SpecialPoints>>
    ) -> Unit,
    modifier: Modifier = Modifier
) {
    var teamScores by remember { mutableStateOf(teams.associate { it.id to 0 }) }
    var teamDeclarations by remember { mutableStateOf(teams.associate { it.id to mutableMapOf<DeclarationType, Int>() }) }
    var teamSpecialPoints by remember { mutableStateOf(teams.associate { it.id to mutableSetOf<SpecialPoints>() }) }

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
                            value = teamScores.getValue(team.id),
                            label = stringResource(Res.string.team_score, team.name),
                            onValueChange = { newValue ->
                                val newScore = newValue.toInt()

                                if (playerCount.count == 4) {
                                    val otherTeamId = teams.find {
                                        it.id != team.id
                                    }?.id ?: error("Team not found")

                                    val remainingScore = TOTAL_SCORE_WITHOUT_SPECIAL_POINTS - newScore

                                    teamScores = teamScores.toMutableMap().apply {
                                        put(team.id, newScore)
                                        put(otherTeamId, remainingScore)
                                    }
                                } else {
                                    teamScores = teamScores.toMutableMap().apply {
                                        put(team.id, newScore)
                                    }
                                }
                            }
                        )

                        DeclarationStepper(
                            declarationType = DeclarationType.TWENTY,
                            onCountChange = { count ->
                                teamDeclarations = teamDeclarations.toMutableMap().apply {
                                    getValue(team.id)[DeclarationType.TWENTY] = count
                                }
                            }
                        )

                        DeclarationStepper(
                            declarationType = DeclarationType.FIFTY,
                            onCountChange = { count ->
                                teamDeclarations = teamDeclarations.toMutableMap().apply {
                                    getValue(team.id)[DeclarationType.FIFTY] = count
                                }
                            }
                        )

                        DeclarationStepper(
                            declarationType = DeclarationType.HUNDRED,
                            onCountChange = { count ->
                                teamDeclarations = teamDeclarations.toMutableMap().apply {
                                    getValue(team.id)[DeclarationType.HUNDRED] = count
                                }
                            }
                        )

                        DeclarationStepper(
                            declarationType = DeclarationType.ONE_FIFTY,
                            onCountChange = { count ->
                                teamDeclarations = teamDeclarations.toMutableMap().apply {
                                    getValue(team.id)[DeclarationType.ONE_FIFTY] = count
                                }
                            }
                        )

                        DeclarationStepper(
                            declarationType = DeclarationType.TWO_HUNDRED,
                            onCountChange = { count ->
                                teamDeclarations = teamDeclarations.toMutableMap().apply {
                                    getValue(team.id)[DeclarationType.TWO_HUNDRED] = count
                                }
                            }
                        )

                        SpecialPointsSwitch(
                            specialPoints = SpecialPoints.BELA,
                            onCheckedChange = { checked ->
                                teamSpecialPoints = teamSpecialPoints.toMutableMap().apply {
                                    if (checked) {
                                        getValue(team.id).add(SpecialPoints.BELA)
                                    } else {
                                        getValue(team.id).remove(SpecialPoints.BELA)
                                    }
                                }
                            }
                        )

                        SpecialPointsSwitch(
                            specialPoints = SpecialPoints.STIGLJA,
                            onCheckedChange = { checked ->
                                teamSpecialPoints = teamSpecialPoints.toMutableMap().apply {
                                    if (checked) {
                                        getValue(team.id).add(SpecialPoints.STIGLJA)
                                    } else {
                                        getValue(team.id).remove(SpecialPoints.STIGLJA)
                                    }
                                }
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
                    onClick = {
                        onConfirm(
                            teamScores,
                            teamDeclarations,
                            teamSpecialPoints
                        )
                    }
                ) {
                    Text(text = stringResource(Res.string.confirm))
                }
            }
        }
    }
}
