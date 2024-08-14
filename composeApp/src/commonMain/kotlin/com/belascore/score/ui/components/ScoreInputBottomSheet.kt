package com.belascore.score.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.ui.text.input.ImeAction
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

// Data class to hold scores for a team
data class TeamScore(
    val score: Int = 0,
    val declarations: MutableMap<DeclarationType, Int> = mutableMapOf(),
    val specialPoints: MutableSet<SpecialPoints> = mutableSetOf()
)

// Data class to hold scores for all teams
data class TeamScores(val scores: Map<Long, TeamScore>)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreInputBottomSheet(
    sheetState: SheetState,
    teams: List<TeamUiState>,
    playerCount: PlayerCount,
    onDismissRequest: () -> Unit,
    onConfirm: (TeamScores) -> Unit,
    modifier: Modifier = Modifier
) {
    var teamScores by remember { mutableStateOf(teams.associate { it.id to TeamScore() }) }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        modifier = modifier
    ) {
        BottomSheetContent(
            teams = teams,
            teamScores = teamScores,
            onTeamScoreChange = { teamId, newScore ->
                teamScores = teamScores.toMutableMap().apply {
                    this[teamId] = newScore
                    if (playerCount == PlayerCount.FOUR) {
                        val otherTeamId =
                            teams.find { it.id != teamId }?.id ?: error("Team not found")
                        this[otherTeamId] = this.getValue(otherTeamId).copy(
                            score = TOTAL_SCORE_WITHOUT_SPECIAL_POINTS - newScore.score
                        )
                    }
                }
            },
            onConfirm = { onConfirm(TeamScores(teamScores)) },
            onCancel = onDismissRequest
        )
    }
}

@Composable
private fun BottomSheetContent(
    teams: List<TeamUiState>,
    teamScores: Map<Long, TeamScore>,
    onTeamScoreChange: (Long, TeamScore) -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(state = rememberScrollState())
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TeamScoreInputs(
            teams = teams,
            teamScores = teamScores,
            onTeamScoreChange = onTeamScoreChange
        )

        Spacer(modifier = Modifier.height(24.dp))

        ConfirmationButtons(
            onConfirm = onConfirm,
            onCancel = onCancel
        )
    }
}

@Composable
private fun TeamScoreInputs(
    teams: List<TeamUiState>,
    teamScores: Map<Long, TeamScore>,
    onTeamScoreChange: (Long, TeamScore) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        teams.forEachIndexed { index, team ->
            TeamScoreInput(
                team = team,
                teamScore = teamScores.getValue(team.id),
                onTeamScoreChange = { newScore -> onTeamScoreChange(team.id, newScore) },
                imeAction = if (index == teams.lastIndex) ImeAction.Done else ImeAction.Next
            )
        }
    }
}

@Composable
private fun RowScope.TeamScoreInput(
    team: TeamUiState,
    teamScore: TeamScore,
    onTeamScoreChange: (TeamScore) -> Unit,
    imeAction: ImeAction
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Score Input
        ScoreInput(
            value = teamScore.score,
            label = stringResource(Res.string.team_score, team.name),
            imeAction = imeAction,
            onValueChange = { newValue ->
                val newScoreValue = newValue.toIntOrNull() ?: 0
                onTeamScoreChange(teamScore.copy(score = newScoreValue))
            }
        )

        // Declaration Steppers
        DeclarationType.entries.forEach { declarationType ->
            DeclarationStepper(
                declarationType = declarationType,
                onCountChange = { count ->
                    onTeamScoreChange(
                        teamScore.copy(
                            declarations = teamScore.declarations.toMutableMap().apply {
                                this[declarationType] = count
                            }
                        )
                    )
                }
            )
        }

        // Special Points Switches
        SpecialPoints.entries.forEach { specialPoints ->
            SpecialPointsSwitch(
                specialPoints = specialPoints,
                onCheckedChange = { checked ->
                    onTeamScoreChange(
                        teamScore.copy(
                            specialPoints = teamScore.specialPoints.toMutableSet().apply {
                                if (checked) add(specialPoints) else remove(specialPoints)
                            }
                        )
                    )
                }
            )
        }
    }
}

@Composable
private fun ConfirmationButtons(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Row {
        FilledTonalButton(onClick = onCancel) {
            Text(text = stringResource(Res.string.cancel))
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(onClick = onConfirm) {
            Text(text = stringResource(Res.string.confirm))
        }
    }
}
