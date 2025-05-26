package com.example.memorizingwords.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.memorizingwords.ui.components.EditText
import com.example.memorizingwords.viewmodel.AddJapaneseWordViewModel

@Preview
@Composable
fun PreviewAddJapaneseWordScreen() {
    AddJapaneseWordScreen()
}

@Composable
fun AddJapaneseWordRoute(
    modifier: Modifier = Modifier,
    viewModel: AddJapaneseWordViewModel = hiltViewModel()
) {

    AddJapaneseWordScreen(
        modifier = modifier,
        onKanjiTextChange = viewModel::changeKanji,
        onHiraganaTextChange = viewModel::changeHiragana,
        onKoreanTextChange = viewModel::changeKorean,
        onClickAddBtn = viewModel::addNewWord
    )
}


@Composable
fun AddJapaneseWordScreen(
    modifier: Modifier = Modifier,
    onKanjiTextChange: (kanji: String) -> Unit = {},
    onHiraganaTextChange: (hiragana: String) -> Unit = {},
    onKoreanTextChange: (korean: String) -> Unit = {},
    onClickAddBtn: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        EditText(
            placeholder = "kanji",
            borderColor = Color.Gray,
            borderSize = 1.dp,
            paddingVertical = 5.dp,
            paddingHorizontal = 5.dp,
            onValueChange = onKanjiTextChange,
            modifier = Modifier
                .fillMaxWidth()
        )

        EditText(
            placeholder = "hiragana",
            borderColor = Color.Gray,
            borderSize = 1.dp,
            paddingVertical = 5.dp,
            paddingHorizontal = 5.dp,
            onValueChange = onHiraganaTextChange,
            modifier = Modifier
                .fillMaxWidth()
        )

        EditText(
            placeholder = "korean",
            borderColor = Color.Gray,
            borderSize = 1.dp,
            paddingVertical = 5.dp,
            paddingHorizontal = 5.dp,
            onValueChange = onKoreanTextChange,
            modifier = Modifier
                .fillMaxWidth()
        )


        Button(
            onClick = onClickAddBtn
        ) {
            Text(
                text = "Add Word"
            )
        }
    }


}