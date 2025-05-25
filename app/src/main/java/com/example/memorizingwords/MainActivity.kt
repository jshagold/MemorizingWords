package com.example.memorizingwords

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitTouchSlopOrCancellation
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.memorizingwords.ui.theme.MemorizingWordsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MemorizingWordsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Androidsssff",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MemorizingWordsTheme {
        Greeting("Android")
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var points by remember { mutableStateOf<List<Offset>>(emptyList()) }
    var pathList by remember { mutableStateOf<List<Path>>(emptyList()) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)

    ) {
        Text(
            text = "おはよう 嘘 $name!",
            modifier = modifier
        )


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            AutoResizingText(
                text = "朝",
                modifier = Modifier
                    .fillMaxSize()
            )


            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        awaitEachGesture {
                            val down = awaitFirstDown()
                            // touch
                            Log.d("TAG", "Greeting: onTap offset = $down.position")
                            points = listOf(down.position)


                            val change = awaitTouchSlopOrCancellation(down.id) { change, offset ->
                                change.consume()
                            }

                            if (change != null) {
                                drag(change.id) { pointerInputChange ->
                                    // drag
                                    Log.e(
                                        "TAG",
                                        "Greeting: onDrag offset = ${pointerInputChange.position}, result: $pointerInputChange"
                                    )
                                    val newPoint = pointerInputChange.position
                                    points = points + newPoint
                                    Log.e("TAG", "Greeting: points size ${points.size}", )
                                }

                                // touch end
                                Log.d("TAG", "Greeting: onDragEnd",)
                                if (points.isNotEmpty()) {
                                    val path = Path().apply {
                                        val firstPoint = points.first()
                                        val rest = points.subList(1, points.size - 1)

                                        moveTo(firstPoint.x, firstPoint.y)
                                        rest.forEach {
                                            lineTo(it.x, it.y)
                                        }
                                    }
                                    pathList = pathList + path
                                    Log.e("TAG", "Greeting: path size ${pathList.size}", )
                                }
                            } else {
                                Log.e(
                                    "TAG",
                                    "Greeting: Drag threshold is not passed and last pointer is up",
                                )
                                // touch end
                                Log.d("TAG", "Greeting: onDragEnd",)
                                if (points.size > 1) {
                                    val path = Path().apply {
                                        val firstPoint = points.first()
                                        val rest = points.subList(1, points.size - 1)

                                        moveTo(firstPoint.x, firstPoint.y)
                                        rest.forEach {
                                            lineTo(it.x, it.y)
                                        }
                                    }
                                    pathList = pathList + path
                                    Log.e("TAG", "Greeting: path size ${pathList.size}", )
                                } else if (points.size == 1) {

                                } else {

                                }
                            }
                        }

                    }
            ) {



                if (points.size > 1) {
                    val path = Path().apply {
                        val firstPoint = points.first()
                        val rest = points.subList(1, points.size - 1)

                        moveTo(firstPoint.x, firstPoint.y)
                        rest.forEach {
                            lineTo(it.x, it.y)
                        }
                    }
                    drawPath(path, Color.Black, style = Stroke(width = 30f, cap = StrokeCap.Round))
                }


                if(pathList.isNotEmpty()) {
                    pathList.forEach {
                        drawPath(it, Color.Black, style = Stroke(width = 30f, cap = StrokeCap.Round))
                    }
                }


            }
        }
        Button(
            onClick = {
                val list2 = pathList.toMutableList()
                list2.removeLastOrNull()
                pathList = list2
            }
        ) {
            Text(
                text = "back"
            )
        }

        Button(
            onClick = {
                points = emptyList()
                pathList = emptyList()
            },
            modifier = Modifier
                .semantics(mergeDescendants = true) {
                    contentDescription = "erase_btn"
                    stateDescription = "erase"
                }
        ) {
            Text(
                text = "erase"
            )
        }
    }
}

@Composable
fun AutoResizingText(
    text: String,
    modifier: Modifier = Modifier,
    maxFontSize: TextUnit = 3000.sp,
    minFontSize: TextUnit = 12.sp,
    step: Int = 2,
    maxLines: Int = 1
) {
    val density = LocalDensity.current
    val measurer = rememberTextMeasurer()
    var textSize by remember { mutableStateOf(maxFontSize) }

    var containerSize by remember { mutableStateOf(IntSize.Zero) }

    LaunchedEffect(text, containerSize) {
        var currentSize = maxFontSize
        while (currentSize >= minFontSize) {
            val textLayoutResult = measurer.measure(
                text = AnnotatedString(text),
                style = TextStyle(fontSize = currentSize),
                constraints = Constraints(
                    maxWidth = containerSize.width,
                    maxHeight = containerSize.height
                ),
                maxLines = maxLines
            )
            if (!textLayoutResult.hasVisualOverflow) {
                break
            }
            currentSize = (currentSize.value - step).sp
        }
        textSize = currentSize
    }

    Box(
        modifier = modifier
            .onSizeChanged { containerSize = it }
    ) {
        Text(
            text = text,
            color = Color.Gray,
            fontSize = textSize,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis
        )
    }
}
