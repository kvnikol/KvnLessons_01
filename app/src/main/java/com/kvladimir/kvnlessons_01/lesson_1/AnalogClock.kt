package com.kvladimir.kvnlessons_01.lesson_1

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kvladimir.kvnlessons_01.R
import com.kvladimir.kvnlessons_01.ui.theme.KvnLessons_01Theme
import com.kvladimir.kvnlessons_01.ui.theme.darkGray
import com.kvladimir.kvnlessons_01.ui.theme.gray
import com.kvladimir.kvnlessons_01.ui.theme.red
import kotlinx.coroutines.delay
import java.util.Calendar
import java.util.Date
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AnalogClock(
    modifier: Modifier = Modifier,
    time:()->Long,
    circleRadius: Int,
    outerCircleThickness:Float,
) {
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    Box(
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()

        ) {

                //переменные круга
            val width = size.width
            val height = size.height
            circleCenter = Offset(x = width / 2f, y = height / 2f)
                // переменные даты
            val date = Date(time())
            val cal = Calendar.getInstance()
            cal.time = date
            val hours = cal.get(Calendar.HOUR_OF_DAY)
            val minutes = cal.get(Calendar.MINUTE)
            val seconds = cal.get(Calendar.SECOND)
            val rectSize = 350.dp



            drawCircle(
                style = Stroke(
                    width = outerCircleThickness
                ),
                brush = Brush.linearGradient(
                    listOf(
                        darkGray.copy(0.45f),
                        darkGray.copy(0.35f)
                    )
                ),
                radius = circleRadius+outerCircleThickness/2f,
                center = circleCenter
            )
            drawCircle(
                color = Color.Transparent,
//                brush = Brush.radialGradient(
//                    listOf(
//                        white.copy(0.45f),
//                        white.copy(0.25f)
//                    )
//                ),
                radius = circleRadius.toFloat(),
                center = circleCenter
            )
            drawCircle(
                color = gray,
                radius = 6f,
                center = circleCenter
            )
            // переменные линий(стрелок)____________________________________________________________
            val littleLineLength = circleRadius*0.1f
            val largeLineLength = circleRadius*0.2f
   //_______________________цикл(циферблат)
            for(i in 0 until 60) {
                val angleInDegrees = i * 360f / 60
                val angleInRad = angleInDegrees * PI / 180f + PI / 2f
                val lineLength = if (i % 5 == 0) largeLineLength else littleLineLength
                val lineThickness = if (i % 5 == 0) 3f else 1f
                val lineHeight = 60f

                val start = Offset(
                    x = (circleRadius * cos(angleInRad) + circleCenter.x).toFloat(),
                    y = (circleRadius * sin(angleInRad) + circleCenter.y).toFloat()
                )

                val end = Offset(
                    x = (circleRadius * cos(angleInRad) + circleCenter.x).toFloat(),
                    y = (circleRadius * sin(angleInRad) + lineLength + circleCenter.y).toFloat()
                )
                rotate(
                    angleInDegrees+180,
                    pivot = start
                ){
                    drawLine(
                        color = gray,
                        start = start,
                        end = end,
                        strokeWidth = lineThickness.dp.toPx()
                    )
                }

            }
    //---------------------------------------end--переменные линий(стрелок часов)
            val clockHands = listOf(ClockHands.Seconds,ClockHands.Minutes,ClockHands.Hours)

            clockHands.forEach { clockHand ->
                val angleInDegrees = when (clockHand) {
                    ClockHands.Seconds -> {
                        seconds * 360f/60f
                    }
                    ClockHands.Minutes -> {
                        (minutes + seconds/60f) * 360f/60f
                    }
                    ClockHands.Hours -> {
                        (((hours%12)/12f*60f)+minutes/12f) * 360f/60f
                    }
                }

                val lineLength = when(clockHand){
                    ClockHands.Seconds -> {
                        circleRadius * 0.8f
                    }
                    ClockHands.Minutes -> {
                        circleRadius * 0.75f
                    }
                    ClockHands.Hours -> {
                        circleRadius * 0.65f
                    }
                }
                val lineThickness = when(clockHand){
                    ClockHands.Seconds -> {
                        0.5f
                    }
                    ClockHands.Minutes -> {
                        2f
                    }
                    ClockHands.Hours -> {
                        3f
                    }
                }
                val start = Offset(
                    x = circleCenter.x,
                    y = circleCenter.y
                )

                val end = Offset(
                    x = circleCenter.x,
                    y = lineLength + circleCenter.y
                )
                rotate(
                    angleInDegrees-180,
                    pivot = start
                ){
                    drawLine(
                        color = if(clockHand == ClockHands.Seconds) red else gray,
                        start = start,
                        end = end,
                        strokeWidth = lineThickness.dp.toPx()
                    )
                }
            }


        }
    }
}
enum class ClockHands {
    Seconds,
    Minutes,
    Hours
}

    @Composable
    fun AnalogClockPreview() {
        var currentTimeInMs by remember {
            mutableLongStateOf(System.currentTimeMillis())
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
            AnalogClock(
                modifier = Modifier
                   .padding( 10.dp)
                . paint(
                    painterResource(id = R.drawable.fon2),
                            contentScale = ContentScale.Fit
                        )
                   // .background(orange)
                    .size(width = sizeClock.dp, height = sizeClock.dp)
                //.clip(CircleShape)
                , time = { currentTimeInMs },
                circleRadius = (sizeClock),
                outerCircleThickness = 7f

            )


        }
    }


    @Preview(showBackground = true)
    @Composable
    fun AnalogClockStartPreview() {
        AnalogClockPreview()
    }
