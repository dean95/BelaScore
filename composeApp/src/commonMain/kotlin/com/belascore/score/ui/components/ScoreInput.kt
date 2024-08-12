package com.belascore.score.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.belascore.game.domain.model.TOTAL_SCORE_WITHOUT_SPECIAL_POINTS

@Composable
fun ScoreInput(
    value: Int,
    label: String,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Default,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        modifier = modifier,
        value = value.toString(),
        onValueChange = { newValue ->
            if (newValue.all(Char::isDigit)) {
                val intValue = newValue.toIntOrNull() ?: 0
                if (intValue <= TOTAL_SCORE_WITHOUT_SPECIAL_POINTS) {
                    onValueChange(intValue.toString())
                }
            }
        },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        ),
        singleLine = true
    )
}
