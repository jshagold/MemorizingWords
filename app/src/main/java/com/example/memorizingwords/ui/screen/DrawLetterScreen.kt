package com.example.memorizingwords.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitTouchSlopOrCancellation
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.memorizingwords.ui.components.AutoResizingText
import com.example.memorizingwords.ui.theme.MemorizingWordsTheme



@Preview(showBackground = true)
@Composable
fun PreviewDrawLetterScreen() {
    MemorizingWordsTheme {
        DrawLetterScreen(letter = "Android")
    }
}

@Composable
fun DrawLetterRoute(
    letter: String
) {


    DrawLetterScreen(
        modifier = Modifier,
        letter = letter
    )
}

@Composable
fun DrawLetterScreen(
    modifier: Modifier = Modifier,
    letter: String
) {
    var points by remember { mutableStateOf<List<Offset>>(emptyList()) }
    var pathList by remember { mutableStateOf<List<Path>>(emptyList()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)

    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            AutoResizingText(
                text = letter,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
            )

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .border(width = 2.dp, Color.Black, RoundedCornerShape(3.dp))
                    .pointerInput(Unit) {
                        awaitEachGesture {
                            val down = awaitFirstDown()
                            // touch
                            points = listOf(down.position)


                            val change = awaitTouchSlopOrCancellation(down.id) { change, offset ->
                                change.consume()
                            }

                            if (change != null) {
                                drag(change.id) { pointerInputChange ->
                                    // drag
                                    val newPoint = pointerInputChange.position
                                    points = points + newPoint
                                }

                                // touch end
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
                                }
                            } else {
                                // touch end
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


        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),

            ) {
            Button(
                onClick = {
                    val list2 = pathList.toMutableList()
                    list2.removeLastOrNull()
                    pathList = list2
                    points = emptyList()
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
}