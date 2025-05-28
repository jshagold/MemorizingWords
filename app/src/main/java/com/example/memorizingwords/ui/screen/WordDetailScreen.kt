package com.example.memorizingwords.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.memorizingwords.model.JapaneseWord
import com.example.memorizingwords.model.PartOfSpeechType
import com.example.memorizingwords.state.WordDetailScreenState
import com.example.memorizingwords.ui.theme.FontSize
import com.example.memorizingwords.viewmodel.WordDetailViewModel


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
fun WordDetailRoute(
    onBackScreen: () -> Unit,
    navigateToDrawLetter: (letter: String) -> Unit = {},
    viewModel: WordDetailViewModel = hiltViewModel()
) {

    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    WordDetailScreen(
        modifier = Modifier,
        screenState = screenState,
        onBackScreen = onBackScreen,
        onUpdateWord = {},
        onDeleteWord = {
            viewModel.deleteWord {
                onBackScreen()
            }
        },
        onClickDrawBtn = {
            navigateToDrawLetter(screenState.word.kanji ?: "")
        }
    )
}


@Composable
fun WordDetailScreen(
    modifier: Modifier = Modifier,
    screenState: WordDetailScreenState,
    onBackScreen: () -> Unit = {},
    onUpdateWord: () -> Unit = {},
    onDeleteWord: () -> Unit = {},
    onClickDrawBtn: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        Text(
            text = "<- ${screenState.word.id}",
            style = TextStyle(fontSize = FontSize.Main, fontWeight = FontWeight.Black),
            modifier = Modifier
                .border(2.dp, Color.Black, RoundedCornerShape(5.dp))
                .padding(10.dp)
                .clickable {
                    onBackScreen()
                }
        )

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val (
                labelKanjiRef, valueKanjiRef,
                labelHiraganaRef, valueHiraganaRef,
                labelKoreanRef, valueKoreanRef,
                labelPOSRef, valuePOSRef,
                labelExampleRef, valueExampleRef,
                labelFavoriteRef, valueFavoriteRef,
                labelDateRef, valueDateRef,
            ) = createRefs()
            val guideLine = createGuidelineFromStart(0.33f)
            val barrierKanji = createBottomBarrier(labelKanjiRef, valueKanjiRef, margin = 10.dp)
            val barrierHiragana = createBottomBarrier(labelHiraganaRef, valueHiraganaRef, margin = 10.dp)
            val barrierKorean = createBottomBarrier(labelKoreanRef, valueKoreanRef, margin = 10.dp)
            val barrierPOS = createBottomBarrier(labelPOSRef, valuePOSRef, margin = 10.dp)
            val barrierExample = createBottomBarrier(labelExampleRef, valueExampleRef, margin = 10.dp)
            val barrierFavorite = createBottomBarrier(labelFavoriteRef, valueFavoriteRef, margin = 10.dp)
            val barrierDate = createBottomBarrier(labelDateRef, valueDateRef, margin = 10.dp)

            // Kanji
            Text(
                text = "한자",
                textAlign = TextAlign.End,
                style = TextStyle(fontSize = FontSize.Sub),
                modifier = Modifier
                    .constrainAs(labelKanjiRef) {
                        top.linkTo(parent.top)
                        bottom.linkTo(valueKanjiRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(guideLine, 10.dp)
                        width = Dimension.fillToConstraints
                    }
            )
            Text(
                text = screenState.word.kanji ?: "",
                style = TextStyle(fontSize = FontSize.Main),
                modifier = Modifier
                    .constrainAs(valueKanjiRef) {
                        top.linkTo(parent.top)
                        start.linkTo(guideLine, 10.dp)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            )

            // Hiragana
            Text(
                text = "히라가나",
                textAlign = TextAlign.End,
                style = TextStyle(fontSize = FontSize.Sub),
                modifier = Modifier
                    .constrainAs(labelHiraganaRef) {
                        top.linkTo(barrierKanji)
                        bottom.linkTo(valueHiraganaRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(guideLine, 10.dp)
                        width = Dimension.fillToConstraints
                    }
            )
            Text(
                text = screenState.word.hiragana,
                style = TextStyle(fontSize = FontSize.Main),
                modifier = Modifier
                    .constrainAs(valueHiraganaRef) {
                        top.linkTo(barrierKanji)
                        start.linkTo(guideLine, 10.dp)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            )

            // Korean
            Text(
                text = "한글",
                textAlign = TextAlign.End,
                style = TextStyle(fontSize = FontSize.Sub),
                modifier = Modifier
                    .constrainAs(labelKoreanRef) {
                        top.linkTo(barrierHiragana)
                        bottom.linkTo(valueKoreanRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(guideLine, 10.dp)
                        width = Dimension.fillToConstraints
                    }
            )
            Text(
                text = screenState.word.korean.toString(),
                style = TextStyle(fontSize = FontSize.Main),
                modifier = Modifier
                    .constrainAs(valueKoreanRef) {
                        top.linkTo(barrierHiragana)
                        start.linkTo(guideLine, 10.dp)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            )

            // POS
            Text(
                text = "품사",
                textAlign = TextAlign.End,
                style = TextStyle(fontSize = FontSize.Sub),
                modifier = Modifier
                    .constrainAs(labelPOSRef) {
                        top.linkTo(barrierKorean)
                        bottom.linkTo(valuePOSRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(guideLine, 10.dp)
                        width = Dimension.fillToConstraints
                    }
            )
            Text(
                text = screenState.word.partOfSpeech.korean,
                style = TextStyle(fontSize = FontSize.Main),
                modifier = Modifier
                    .constrainAs(valuePOSRef) {
                        top.linkTo(barrierKorean)
                        start.linkTo(guideLine, 10.dp)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            )

            // Example
            Text(
                text = "예문",
                textAlign = TextAlign.End,
                style = TextStyle(fontSize = FontSize.Sub),
                modifier = Modifier
                    .constrainAs(labelExampleRef) {
                        top.linkTo(barrierPOS)
                        bottom.linkTo(valueExampleRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(guideLine, 10.dp)
                        width = Dimension.fillToConstraints
                    }
            )
            Text(
                text = screenState.word.exampleSentence.toString(),
                style = TextStyle(fontSize = FontSize.Main),
                modifier = Modifier
                    .constrainAs(valueExampleRef) {
                        top.linkTo(barrierPOS)
                        start.linkTo(guideLine, 10.dp)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            )

            // Favorite
            Text(
                text = "즐겨찾기",
                textAlign = TextAlign.End,
                style = TextStyle(fontSize = FontSize.Sub),
                modifier = Modifier
                    .constrainAs(labelFavoriteRef) {
                        top.linkTo(barrierExample)
                        bottom.linkTo(valueFavoriteRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(guideLine, 10.dp)
                        width = Dimension.fillToConstraints
                    }
            )
            Text(
                text = screenState.word.isFavorite.toString(),
                style = TextStyle(fontSize = FontSize.Main),
                modifier = Modifier
                    .constrainAs(valueFavoriteRef) {
                        top.linkTo(barrierExample)
                        start.linkTo(guideLine, 10.dp)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            )

            // Last Update Date
            Text(
                text = "마지막 업데이트",
                textAlign = TextAlign.End,
                style = TextStyle(fontSize = FontSize.Sub),
                modifier = Modifier
                    .constrainAs(labelDateRef) {
                        top.linkTo(barrierFavorite)
                        start.linkTo(parent.start)
                        end.linkTo(guideLine, 10.dp)
                        width = Dimension.fillToConstraints
                    }
            )
            Text(
                text = screenState.word.lastStudiedAt.toString(),
                style = TextStyle(fontSize = FontSize.Main),
                modifier = Modifier
                    .constrainAs(valueDateRef) {
                        top.linkTo(barrierFavorite)
                        start.linkTo(guideLine, 10.dp)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
        ) {
            Button(
                onClick = onUpdateWord
            ) {
                Text(
                    text = "수정"
                )
            }

            Button(
                onClick = onDeleteWord
            ) {
                Text(
                    text = "삭제"
                )
            }

            Button(
                onClick = onClickDrawBtn
            ) {
                Text(
                    text = "Draw Kanji"
                )
            }
        }

    }
}