package com.example.memorizingwords.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

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
                maxLines = maxLines,
                softWrap = false,
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
            .onSizeChanged { containerSize = IntSize(it.width - 10, it.height) }
    ) {
        Text(
            text = text,
            color = Color.Gray,
            fontSize = textSize,
            maxLines = maxLines,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}