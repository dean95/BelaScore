package com.belascore.score.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

private const val MAX_SCORE = 162

@Composable
fun ScoreInput(
    value: Int,
    label: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        modifier = modifier,
        value = value.toString(),
        onValueChange = { newValue ->
            if (newValue.all(Char::isDigit) && newValue.toInt() <= MAX_SCORE) {
                onValueChange(newValue.ifBlank { "0" })
            }
        },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true
    )
}
