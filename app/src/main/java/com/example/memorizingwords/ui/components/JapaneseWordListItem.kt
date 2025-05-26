package com.example.memorizingwords.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.memorizingwords.model.JapaneseWord
import com.example.memorizingwords.model.PartOfSpeechType


@Preview
@Composable
fun PreviewJapaneseWordListItem() {
    val word = JapaneseWord(
        kanji = "悪いaaaaaaaaaaaaaaaaaaaaaaaa",
        hiragana = "わるい",
        korean = listOf("나쁘다"),
        partOfSpeech = PartOfSpeechType.ADJECTIVE,
        exampleSentence = listOf(),
        createdAt = System.currentTimeMillis(),
        lastStudiedAt = System.currentTimeMillis()
    )
    JapaneseWordListItem(
        word = word
    )
}

@Composable
fun JapaneseWordListItem(
    modifier: Modifier = Modifier,
    word: JapaneseWord,
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(5.dp))
            .padding(5.dp)
    ) {
        val (kanjiText, hiraganaText, koreanText) = createRefs()
        val firstGuideLine = createGuidelineFromStart(0.33f)
        val secondGuideLine = createGuidelineFromStart(0.66f)

        Text(
            text = word.kanji.toString(),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .constrainAs(kanjiText) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(firstGuideLine)
                    width = Dimension.fillToConstraints
                }
        )

        Text(
            text = word.hiragana,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .constrainAs(hiraganaText) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(firstGuideLine)
                    end.linkTo(secondGuideLine)
                    width = Dimension.fillToConstraints
                }
        )

        Text(
            text = word.korean.toString(),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .constrainAs(koreanText) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(secondGuideLine)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )
    }
}