package com.belascore.score.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Badge
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import com.belascore.game.domain.model.TOTAL_SCORE_WITHOUT_SPECIAL_POINTS

@Composable
fun ScoreInput(
    value: Int,
    bonusPoints: Int,
    label: String,
    onValueChange: (String) -> Unit,
    onFocusChanged: (FocusState) -> Unit,
    imeAction: ImeAction = ImeAction.Default,
    modifier: Modifier = Modifier
) {
    val valueStr = value.toString()
    val textFieldValue = TextFieldValue(
        text = valueStr,
        selection = TextRange(valueStr.length)
    )

    OutlinedTextField(
        modifier = modifier.onFocusChanged(onFocusChanged),
        value = textFieldValue,
        onValueChange = { newValue ->
            val newValueStr = newValue.text
            if (newValueStr.all(Char::isDigit)) {
                val intValue = newValueStr.toIntOrNull() ?: 0
                if (intValue <= TOTAL_SCORE_WITHOUT_SPECIAL_POINTS) {
                    onValueChange(intValue.toString())
                }
            }
        },
        label = {
            Text(
                text = label,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        ),
        singleLine = true,
        trailingIcon = {
            if (bonusPoints > 0) {
                Badge {
                    Text("+$bonusPoints")
                }
            }
        }
    )
}
