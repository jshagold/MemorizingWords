package com.example.memorizingwords.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.memorizingwords.model.JapaneseWordType
import com.example.memorizingwords.state.RandomWordScreenState
import com.example.memorizingwords.viewmodel.RandomWordViewModel
import org.checkerframework.checker.units.qual.C


@Preview
@Composable
fun PreviewRandomWordScreen() {
    RandomWordScreen(
        screenState = RandomWordScreenState(),
        onClickTextCard = {},
        onClickNextBtn = {}
    )
}

@Composable
fun RandomWordRoute(
    viewModel: RandomWordViewModel = hiltViewModel()
) {

    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    RandomWordScreen(
        screenState = screenState,
        onClickTextCard = viewModel::onClickTextCard,
        onClickNextBtn = viewModel::onClickNextBtn
    )
}


@Composable
fun RandomWordScreen(
    screenState: RandomWordScreenState,
    onClickTextCard: () -> Unit,
    onClickNextBtn: () -> Unit,
) {
    val text = when(screenState.type) {
        JapaneseWordType.KANJI -> {
            screenState.word.kanji ?: "null"
        }
        JapaneseWordType.HIRAGANA -> {
            screenState.word.hiragana
        }
        JapaneseWordType.KOREAN -> {
            screenState.word.korean.toString()
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (textRef, nextBtnRef) = createRefs()

        Text(
            text = text,
            modifier = Modifier
                .border(1.dp, Color.Gray, RoundedCornerShape(5.dp))
                .padding(10.dp)
                .clickable { onClickTextCard() }
                .constrainAs(textRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(nextBtnRef.start)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )

        Button(
            onClick = onClickNextBtn,
            modifier = Modifier
                .constrainAs(nextBtnRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(textRef.end)
                    end.linkTo(parent.end)
                }
        ) {
            Text("next")
        }
    }
}