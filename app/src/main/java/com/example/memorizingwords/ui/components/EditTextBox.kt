package com.example.memorizingwords.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension


@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun PreviewEditText() {
    val test = ""

    EditText(
        inputText = test,
        placeholder = "PlaceHolder"
    )
}



@Composable
fun EditText(
    modifier: Modifier = Modifier,
    inputText: String = "",
    onValueChange: (value: String) -> Unit = {},
    placeholder: String = "",
    textColor: Color = Black,
    placeholderColor: Color = Gray,
    backgroundColor: Color = Transparent,
    shape: Shape = RoundedCornerShape(5.dp),
    borderSize: Dp = 0.dp,
    borderColor: Color = Transparent,
    paddingVertical: Dp = 0.dp,
    paddingHorizontal: Dp = 0.dp,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = false,
    isFocused: MutableState<Boolean> = remember { mutableStateOf(false) },
) {
    val sharedTextStyle = TextStyle(
        color = textColor
    )

    BasicTextField(
        value = inputText,
        textStyle = TextStyle(
            color = textColor
        ),
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = singleLine,
        modifier = modifier
            .onFocusChanged { state ->
                isFocused.value = state.isFocused
            }
    ) { innerTextField ->
        ConstraintLayout (
            modifier = Modifier
                .background(backgroundColor, shape)
                .border(borderSize, borderColor, shape)
                .padding(
                    vertical = paddingVertical,
                    horizontal = paddingHorizontal,
                )
        ) {
            val (textRef, placeHolderRef) = createRefs()

            Box(
                modifier = Modifier
                    .constrainAs(textRef) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
            ) {
                innerTextField()
            }

            if(inputText.isEmpty()) {
                Text(
                    text = placeholder,
                    style = sharedTextStyle.copy(color = placeholderColor),
                    modifier = Modifier
                        .constrainAs(placeHolderRef) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .fillMaxWidth()
                )
            }
        }
    }
}