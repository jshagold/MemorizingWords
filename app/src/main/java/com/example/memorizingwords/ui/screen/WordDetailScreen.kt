package com.example.memorizingwords.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memorizingwords.model.JapaneseWord
import com.example.memorizingwords.model.PartOfSpeechType
import com.example.memorizingwords.state.WordDetailScreenState
import com.example.memorizingwords.ui.theme.FontSize


@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun PreviewWordDetailScreen() {
    val word = JapaneseWord(
        kanji = "声",
        hiragana = "こえ",
        korean = listOf("목소리"),
        partOfSpeech = PartOfSpeechType.NOUN,
        exampleSentence = listOf("声を上げる。"),
        createdAt = System.currentTimeMillis(),
        lastStudiedAt = System.currentTimeMillis()
    )

    WordDetailScreen(
        screenState = WordDetailScreenState(
            word = word
        )
    )
}


@Composable
fun WordDetailRoute() {



//    WordDetailScreen()
}


@Composable
fun WordDetailScreen(
    modifier: Modifier = Modifier,
    screenState: WordDetailScreenState,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column (
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.7f)
            ) {
                Text(
                    text = "한자 : ",
                    textAlign = TextAlign.End,
                    style = TextStyle(fontSize = FontSize.Main),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = "히라가나 : ",
                    textAlign = TextAlign.End,
                    style = TextStyle(fontSize = FontSize.Main),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = "한글 : ",
                    textAlign = TextAlign.End,
                    style = TextStyle(fontSize = FontSize.Main),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = "품사 : ",
                    textAlign = TextAlign.End,
                    style = TextStyle(fontSize = FontSize.Main),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = "예문 : ",
                    textAlign = TextAlign.End,
                    style = TextStyle(fontSize = FontSize.Main),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = "즐겨찾기 : ",
                    textAlign = TextAlign.End,
                    style = TextStyle(fontSize = FontSize.Main),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = "생성일 : ",
                    textAlign = TextAlign.End,
                    style = TextStyle(fontSize = FontSize.Main),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = "마지막 업데이트 : ",
                    textAlign = TextAlign.End,
                    style = TextStyle(fontSize = FontSize.Main),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Text(
                    text = screenState.word.kanji ?: "",
                    style = TextStyle(fontSize = FontSize.Main)
                )
                Text(
                    text = screenState.word.hiragana,
                    style = TextStyle(fontSize = FontSize.Main)
                )
                Text(
                    text = screenState.word.korean.toString(),
                    style = TextStyle(fontSize = FontSize.Main)
                )
            }

        }


    }
}