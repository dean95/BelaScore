import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import com.belascore.home.ui.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        HomeScreen(
            onNewGameClick = {},
            onGameHistoryClick = {}
        )
    }
}
