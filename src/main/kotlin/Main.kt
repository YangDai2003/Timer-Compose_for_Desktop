import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@OptIn(DelicateCoroutinesApi::class)
@Composable
@Preview
fun App() {
    var isRunning by remember { mutableStateOf(false) }
    var elapsedTime by remember { mutableStateOf(0L) }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val hours = TimeUnit.MILLISECONDS.toHours(elapsedTime)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime) % 60
            val seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime) % 60

            Text("${formatTimeUnit(hours)}:${formatTimeUnit(minutes)}:${formatTimeUnit(seconds)}")

            Button(onClick = {
                isRunning = !isRunning
                if (isRunning) {
                    GlobalScope.launch {
                        while (isRunning) {
                            elapsedTime += 1000L
                            kotlinx.coroutines.delay(1000L)
                        }
                    }
                }
            }) {
                Text(if (isRunning) "Pause" else "Start")
            }

            Button(onClick = {
                isRunning = false
                elapsedTime = 0L
            }) {
                Text("Reset")
            }
        }

    }

}

fun formatTimeUnit(timeUnit: Long): String {
    return if (timeUnit < 10) {
        "0$timeUnit"
    } else {
        timeUnit.toString()
    }
}


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
