package com.example.memorizingwords.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun PreviewDropdownComponent() {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        DropdownComponent(
            selectButtonText = "형용사",
            dataList = listOf("동사","명사","형용사")
        )
    }
}

@Composable
fun DropdownComponent(
    modifier: Modifier = Modifier,
    selectButtonText: String,
    dataList: List<String> = listOf(),
    selectElement: (index: Int) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var buttonWidth by remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier
    ) {
        Text(
            text = selectButtonText,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .onGloballyPositioned {
                    buttonWidth = it.size.width
                }
                .align(Alignment.TopStart)
                .clickable {
                    expanded = !expanded
                }
                .border(2.dp, Color.Black, RoundedCornerShape(5.dp))
                .padding(15.dp)

        )

        DropdownMenu(
            containerColor = Color.White,
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { buttonWidth.toDp() })
        ) {

            dataList.forEachIndexed { index, data ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = data,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    },
                    onClick = {
                        selectElement(index)
                        expanded = false
                    },
                )
            }
        }
    }

}