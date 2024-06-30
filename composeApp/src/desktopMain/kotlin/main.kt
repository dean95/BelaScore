import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.belascore.core.di.initKoin
import com.belascore.coreUi.App

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "BelaScore",
        ) {
            App()
        }
    }
}
