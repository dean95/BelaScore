package com.belascore.score.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.belascore.game.domain.model.TOTAL_SCORE_WITHOUT_SPECIAL_POINTS

@Composable
fun ScoreInput(
    value: Int,
    label: String,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Default,
    modifier: Modifier = Modifier
) {
    val valueStr = value.toString()
    val textFieldValue = TextFieldValue(
        text = valueStr,
        selection = TextRange(valueStr.length)
    )
    OutlinedTextField(
        modifier = modifier,
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
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        ),
        singleLine = true
    )
}
