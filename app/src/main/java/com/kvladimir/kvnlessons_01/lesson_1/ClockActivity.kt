package com.kvladimir.kvnlessons_01.lesson_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kvladimir.kvnlessons_01.lesson_1.timer.TimerScreenContent
import com.kvladimir.kvnlessons_01.lesson_1.timer.TimerViewModel
import com.kvladimir.kvnlessons_01.ui.theme.KvnLessons_01Theme
import com.kvladimir.kvnlessons_01.ui.theme.orange
import kotlinx.coroutines.delay


class ClockActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var currentTimeInMs by remember {
                mutableLongStateOf(System.currentTimeMillis())
            }
            KvnLessons_01Theme {
                val timerViewModel: TimerViewModel by viewModels()
                LaunchedEffect(key1 = true) {
                    while (true) {
                        delay(200)
                        currentTimeInMs = System.currentTimeMillis()
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    AnalogClock(
                        modifier = Modifier
//                . paint(
//                    painterResource(id = R.drawable.fon),
//                            contentScale = ContentScale.Fit
//                        )
                            .background(orange)
                            .size(width = 360.dp, height = 360.dp)
                            .padding(40.dp)
                        //.clip(CircleShape)
                        , time = { currentTimeInMs },
                        circleRadius = 300,
                        outerCircleThickness = 7f

                    )

                    TimerScreenContent(timerViewModel = timerViewModel)
                }
               // RememberExperiment()

            }
        }
    }

   //@SuppressLint("UnrememberedMutableState")
    @Composable
    fun RememberExperiment() {
       // val counter = mutableIntStateOf(0)
        val counter = remember {
            mutableIntStateOf(0)
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Counter1: ${counter.value}")
            Button(onClick = {
                counter.value++
                println("experiment: ${counter.value}")
            }) {
                Text(text = "Increment")
            }
        }
    }
}