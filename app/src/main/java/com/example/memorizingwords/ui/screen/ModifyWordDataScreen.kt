package com.example.memorizingwords.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.memorizingwords.model.JapaneseWord
import com.example.memorizingwords.model.PartOfSpeechType
import com.example.memorizingwords.state.ModifyWordDataScreenState
import com.example.memorizingwords.ui.components.DropdownComponent
import com.example.memorizingwords.ui.components.EditText
import com.example.memorizingwords.ui.theme.FontSize
import com.example.memorizingwords.viewmodel.ModifyWordDataViewModel


@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun PreviewModifyWordDataScreen() {
    ModifyWordDataScreen(
        screenState = ModifyWordDataScreenState(word = JapaneseWord(kanji = "kan", hiragana = "hira", createdAt = 0L, lastStudiedAt = 0L, korean = listOf("1","12"), exampleSentence = listOf(), ))
    )
}

@Composable
fun ModifyWordDataRoute(
    modifier: Modifier = Modifier,
    viewModel: ModifyWordDataViewModel = hiltViewModel(),
    onBackScreen: () -> Unit,
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    ModifyWordDataScreen(
        modifier = modifier,
        screenState = screenState,
        onBackScreen = onBackScreen,
        onChangeKanji = ,
        onChangeHiragana = ,
        onChangeKorean = ,
        onChangePOS = ,
        onChangeExample = ,
        onChangeFavorite = ,
        onModifyBtn =
    )
}


@Composable
fun ModifyWordDataScreen(
    modifier: Modifier = Modifier,
    screenState: ModifyWordDataScreenState,
    onBackScreen: () -> Unit = {},
    onChangeKanji: (value: String) -> Unit = {},
    onChangeHiragana: (value: String) -> Unit = {},
    onChangeKorean: (value: String) -> Unit = {},
    onChangePOS: (index: Int) -> Unit = {},
    onChangeExample: (value: String) -> Unit = {},
    onChangeFavorite: () -> Unit = {},
    onModifyBtn: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            text = "<- 뒤로가기",
            style = TextStyle(fontSize = FontSize.Main, fontWeight = FontWeight.Black),
            modifier = Modifier
                .border(2.dp, Color.Black, RoundedCornerShape(5.dp))
                .padding(10.dp)
                .clickable {
                    onBackScreen()
                }
        )

        // Kanji
        Text(
            text = "한자",
            textAlign = TextAlign.End,
            style = TextStyle(fontSize = FontSize.Sub),
            modifier = Modifier
        )
        EditText(
            inputText = screenState.word.kanji ?: "",
            borderColor = Color.Gray,
            paddingHorizontal = 10.dp,
            paddingVertical = 10.dp,
            onValueChange = onChangeKanji,
            modifier = Modifier
        )

        // Hiragana
        Text(
            text = "히라가나",
            textAlign = TextAlign.End,
            style = TextStyle(fontSize = FontSize.Sub),
            modifier = Modifier
        )
        EditText(
            inputText = screenState.word.hiragana,
            borderColor = Color.Gray,
            paddingHorizontal = 10.dp,
            paddingVertical = 10.dp,
            onValueChange = onChangeHiragana,
            modifier = Modifier,
        )

        // Korean
        Text(
            text = "한글",
            textAlign = TextAlign.End,
            style = TextStyle(fontSize = FontSize.Sub),
            modifier = Modifier
        )
        EditText(
            inputText = screenState.word.korean.toString(),
            borderColor = Color.Gray,
            paddingHorizontal = 10.dp,
            paddingVertical = 10.dp,
            onValueChange = onChangeKorean,
            modifier = Modifier,
        )

        // POS
        Text(
            text = "품사",
            textAlign = TextAlign.End,
            style = TextStyle(fontSize = FontSize.Sub),
            modifier = Modifier
        )
        DropdownComponent(
            selectButtonText = screenState.word.partOfSpeech.korean,
            dataList = PartOfSpeechType.entries.map { it.korean },
            selectElement = onChangePOS,
        )

        // Example
        Text(
            text = "예문",
            textAlign = TextAlign.End,
            style = TextStyle(fontSize = FontSize.Sub),
            modifier = Modifier
        )
        EditText(
            inputText = screenState.word.exampleSentence.toString(),
            borderColor = Color.Gray,
            paddingHorizontal = 10.dp,
            paddingVertical = 10.dp,
            onValueChange = onChangeExample,
            modifier = Modifier,
        )

        // Favorite
        Text(
            text = "즐겨찾기",
            textAlign = TextAlign.End,
            style = TextStyle(fontSize = FontSize.Sub),
            modifier = Modifier
        )
        Text(
            text = "true",
            fontWeight = FontWeight.Black,
            modifier = Modifier
                .border(2.dp, Color.Black, RoundedCornerShape(5.dp))
                .padding(10.dp)
                .clickable {
                    onChangeFavorite()
                }
        )

        Button(
            onClick = onModifyBtn
        ) {
            Text(
                text = "수정"
            )
        }
    }
}