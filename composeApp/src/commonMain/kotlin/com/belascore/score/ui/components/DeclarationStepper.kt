package com.belascore.score.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import belascore.composeapp.generated.resources.Res
import belascore.composeapp.generated.resources.cd_decrease
import belascore.composeapp.generated.resources.cd_increase
import com.belascore.game.domain.model.DeclarationType
import org.jetbrains.compose.resources.stringResource

@Composable
fun DeclarationStepper(
    declarationType: DeclarationType,
    modifier: Modifier = Modifier,
    onCountChange: (Int) -> Unit
) {
    var count by rememberSaveable { mutableStateOf(0) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        IconButton(
            enabled = count > 0,
            onClick = {
                count--
                onCountChange(count)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = stringResource(Res.string.cd_decrease)
            )
        }
        BadgedBox(
            badge = {
                if (count > 0) {
                    Badge(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ) {
                        Text(text = count.toString())
                    }
                }
            }
        ) {
            Text(
                text = declarationType.points.toString(),
                fontSize = 20.sp,
                modifier = Modifier.padding(all = 4.dp)
            )
        }
        IconButton(
            enabled = when (declarationType) {
                DeclarationType.TWENTY, DeclarationType.FIFTY, DeclarationType.HUNDRED -> count < 4
                DeclarationType.ONE_FIFTY, DeclarationType.TWO_HUNDRED -> count < 1
            },
            onClick = {
                count++
                onCountChange(count)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(Res.string.cd_increase)
            )
        }
    }
}
