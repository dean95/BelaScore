package com.belascore.score.ui.components

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import belascore.composeapp.generated.resources.Res
import belascore.composeapp.generated.resources.cancel
import belascore.composeapp.generated.resources.confirm
import com.belascore.game.domain.model.DeclarationType
import com.belascore.game.domain.model.SpecialPoints
import com.belascore.game.domain.model.TOTAL_SCORE_WITHOUT_SPECIAL_POINTS
import com.belascore.newGame.ui.PlayerCount
import com.belascore.score.ui.TeamUiState
import org.jetbrains.compose.resources.stringResource

// Data class to hold scores for a round
data class RoundScore(
    val score: Int = 0,
    val declarations: MutableMap<DeclarationType, Int> = mutableMapOf(),
    val specialPoints: MutableSet<SpecialPoints> = mutableSetOf()
)

// Data class to hold scores for all teams in a round
data class RoundScores(val scores: Map<Long, RoundScore>)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreInputBottomSheet(
    sheetState: SheetState,
    teams: List<TeamUiState>,
    playerCount: PlayerCount,
    onDismissRequest: () -> Unit,
    onConfirm: (RoundScores) -> Unit,
    modifier: Modifier = Modifier
) {
    var roundScores by remember {
        mutableStateOf(
            RoundScores(scores = teams.associate { it.id to RoundScore() })
        )
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        modifier = modifier
    ) {
        BottomSheetContent(
            teams = teams,
            roundScores = roundScores,
            onTeamScoreChange = { teamId, newScore ->
                roundScores = roundScores.copy(
                    scores = roundScores.scores.toMutableMap().apply {
                        this[teamId] = newScore
                        if (playerCount == PlayerCount.FOUR) {
                            updateScoresForFourPlayers(
                                teamScores = this,
                                teamId = teamId,
                                newScore = newScore,
                                teams = teams
                            )
                        }
                    }
                )
            },
            onConfirm = { onConfirm(roundScores) },
            onCancel = onDismissRequest
        )
    }
}

@Composable
private fun BottomSheetContent(
    teams: List<TeamUiState>,
    roundScores: RoundScores,
    onTeamScoreChange: (Long, RoundScore) -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    var focusedTeamId by rememberSaveable { mutableStateOf<Long?>(null) }

    Column(
        modifier = Modifier
            .verticalScroll(state = rememberScrollState())
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TeamScoreInputs(
            teams = teams,
            roundScores = roundScores,
            onTeamScoreChange = onTeamScoreChange,
            onFocused = { focusedTeamId = it }
        )

        AnimatedVisibility(visible = focusedTeamId != null) {
            Column {
                FocusedTeamOptions(
                    focusedTeamId = focusedTeamId,
                    roundScores = roundScores,
                    onTeamScoreChange = onTeamScoreChange
                )
            }

        }

        Spacer(modifier = Modifier.height(24.dp))

        ConfirmationButtons(
            confirmEnabled = focusedTeamId != null,
            onConfirm = onConfirm,
            onCancel = onCancel
        )
    }
}

@Composable
private fun FocusedTeamOptions(
    focusedTeamId: Long?,
    roundScores: RoundScores,
    onTeamScoreChange: (Long, RoundScore) -> Unit
) {
    // Declaration Steppers
    DeclarationType.entries.forEach { declarationType ->
        DeclarationStepper(
            initialCount = focusedTeamId?.let { teamId ->
                roundScores.scores.getValue(teamId).declarations[declarationType]
            } ?: 0,
            declarationType = declarationType,
            onCountChange = { count ->
                focusedTeamId?.let { teamId ->
                    val currentScore = roundScores.scores.getValue(teamId)
                    onTeamScoreChange(
                        teamId,
                        currentScore.copy(
                            declarations = currentScore.declarations.toMutableMap().apply {
                                this[declarationType] = count
                            }
                        )
                    )
                }
            }
        )
    }

    // Special Points Switches
    SpecialPoints.entries.forEach { specialPoints ->
        SpecialPointsSwitch(
            initialChecked = focusedTeamId?.let { teamId ->
                roundScores.scores.getValue(teamId).specialPoints.contains(specialPoints)
            } ?: false,
            specialPoints = specialPoints,
            onCheckedChange = { checked ->
                focusedTeamId?.let { teamId ->
                    val currentScore = roundScores.scores.getValue(teamId)
                    onTeamScoreChange(
                        teamId,
                        currentScore.copy(
                            specialPoints = currentScore.specialPoints.toMutableSet().apply {
                                if (checked) add(specialPoints) else remove(specialPoints)
                            }
                        )
                    )
                }
            }
        )
    }
}

@Composable
private fun TeamScoreInputs(
    onFocused: (Long) -> Unit,
    teams: List<TeamUiState>,
    roundScores: RoundScores,
    onTeamScoreChange: (Long, RoundScore) -> Unit
) {
    if (teams.size <= 2) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RenderTeamScoreInputs(
                teams = teams,
                roundScores = roundScores,
                onTeamScoreChange = onTeamScoreChange,
                onFocused = onFocused,
                modifier = Modifier.weight(1f)
            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            RenderTeamScoreInputs(
                teams = teams,
                roundScores = roundScores,
                onTeamScoreChange = onTeamScoreChange,
                onFocused = onFocused
            )
        }
    }
}

@Composable
private fun RenderTeamScoreInputs(
    teams: List<TeamUiState>,
    roundScores: RoundScores,
    onTeamScoreChange: (Long, RoundScore) -> Unit,
    onFocused: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    teams.forEachIndexed { index, team ->
        val teamScore = roundScores.scores.getValue(team.id)
        ScoreInput(
            value = teamScore.score,
            label = team.name,
            imeAction = if (index == teams.lastIndex) ImeAction.Done else ImeAction.Next,
            onValueChange = { newValue ->
                val newScoreValue = newValue.toIntOrNull() ?: 0
                onTeamScoreChange(team.id, teamScore.copy(score = newScoreValue))
            },
            onFocusChanged = { focusState ->
                if (focusState.isFocused) {
                    onFocused(team.id)
                }
            },
            modifier = modifier.padding(16.dp),
            bonusPoints = teamScore.declarations
                .map { (declaration, count) -> declaration.points * count }
                .sum() + teamScore.specialPoints.sumOf(SpecialPoints::points)
        )
    }
}

@Composable
private fun ConfirmationButtons(
    confirmEnabled: Boolean,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Row {
        FilledTonalButton(onClick = onCancel) {
            Text(text = stringResource(Res.string.cancel))
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            enabled = confirmEnabled,
            onClick = onConfirm
        ) {
            Text(text = stringResource(Res.string.confirm))
        }
    }
}

private fun updateScoresForFourPlayers(
    teamScores: MutableMap<Long, RoundScore>,
    teamId: Long,
    newScore: RoundScore,
    teams: List<TeamUiState>
) {
    val otherTeamId = teams.find { it.id != teamId }?.id ?: error("Team not found")
    teamScores[otherTeamId] = teamScores.getValue(otherTeamId).copy(
        score = TOTAL_SCORE_WITHOUT_SPECIAL_POINTS - newScore.score
    )
}
