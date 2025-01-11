package com.kvladimir.kvnlessons_01.lesson_1.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kvladimir.kvnlessons_01.R
import com.kvladimir.kvnlessons_01.lesson_1.AnalogClockPreview
import com.kvladimir.kvnlessons_01.ui.theme.KvnLessons_01Theme
import kotlinx.coroutines.delay

@Composable
fun TimerScreenContent(timerViewModel: TimerViewModel) {
    val timerValue by timerViewModel.timer.collectAsState()

    TimerScreen(
        timerValue = timerValue,
        onStartClick = { timerViewModel.startTimer() },
        onPauseClick = { timerViewModel.pauseTimer() },
        onStopClick = { timerViewModel.stopTimer() }
    )
}

@Composable
fun TimerScreen(
    timerValue: Long,
    onStartClick: () -> Unit,
    onPauseClick: () -> Unit,
    onStopClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = timerValue.formatTime(), fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = onStartClick) {
                Text(stringResource(R.string.start))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = onPauseClick) {
                Text(stringResource(R.string.pause))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = onStopClick) {

                Text(stringResource( R.string.stop))
            }
        }
    }
}

fun Long.formatTime(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val remainingSeconds = this % 60
    return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
}

@Composable
fun TimerScreenPreview() {
    var currentTimeInMs by remember {
        //mutableLongStateOf(System.currentTimeMillis())
        mutableLongStateOf(0)
    }
    val sizeClock by remember {
        mutableStateOf(300)
    }

    KvnLessons_01Theme {
        LaunchedEffect(key1 = true) {
            while (true) {
                delay(200)
                currentTimeInMs = System.currentTimeMillis()
            }
        }
     TimerScreen(timerValue = currentTimeInMs, onStartClick = {}, onPauseClick = {}, onStopClick = {})


    }
}

@Preview(showBackground = true)
@Composable
fun TimerScreenStartPreview() {
    TimerScreenPreview()
}