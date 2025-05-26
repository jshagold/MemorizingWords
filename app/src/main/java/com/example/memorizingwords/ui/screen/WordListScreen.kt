package com.example.memorizingwords.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.memorizingwords.R
import com.example.memorizingwords.model.JapaneseWord
import com.example.memorizingwords.model.PartOfSpeechType
import com.example.memorizingwords.state.WordListScreenState
import com.example.memorizingwords.ui.components.JapaneseWordListItem
import com.example.memorizingwords.viewmodel.WordListViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow


@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
fun PreviewWordListScreen() {
    val word = JapaneseWord(
        kanji = "悪い",
        hiragana = "わるい",
        korean = listOf("나쁘다"),
        partOfSpeech = PartOfSpeechType.ADJECTIVE,
        exampleSentence = listOf(),
        createdAt = System.currentTimeMillis(),
        lastStudiedAt = System.currentTimeMillis()
    )

    val word2 = JapaneseWord(
        kanji = "大きい",
        hiragana = "おおきい",
        korean = listOf("크다"),
        partOfSpeech = PartOfSpeechType.ADJECTIVE,
        exampleSentence = listOf(),
        createdAt = System.currentTimeMillis(),
        lastStudiedAt = System.currentTimeMillis()
    )


    val fakeData = listOf(
        JapaneseWord(
            kanji = "悪い",
            hiragana = "わるい",
            korean = listOf("나쁘다"),
            partOfSpeech = PartOfSpeechType.ADJECTIVE,
            exampleSentence = listOf(),
            createdAt = System.currentTimeMillis(),
            lastStudiedAt = System.currentTimeMillis()
        ),
        JapaneseWord(
            kanji = "大きい",
            hiragana = "おおきい",
            korean = listOf("크다"),
            partOfSpeech = PartOfSpeechType.ADJECTIVE,
            exampleSentence = listOf(),
            createdAt = System.currentTimeMillis(),
            lastStudiedAt = System.currentTimeMillis()
        )
    )
    val pagingData = PagingData.from(fakeData)
    val fakeDataFlow = MutableStateFlow(pagingData).collectAsLazyPagingItems()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        WordListScreen(
            screenState = WordListScreenState(),
            pagingData = fakeDataFlow
        )
    }
}

@Composable
fun WordListRoute(
    modifier: Modifier = Modifier,
    viewModel: WordListViewModel = hiltViewModel()
) {

    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val pagingWordList = viewModel.japanesePagingWordList.collectAsLazyPagingItems()

    WordListScreen(
        modifier = modifier,
        screenState = screenState,
        pagingData = pagingWordList
    )
}


@Composable
fun WordListScreen(
    modifier: Modifier = Modifier,
    screenState: WordListScreenState,
    pagingData: LazyPagingItems<JapaneseWord>
) {

    Napier.e {
        "${pagingData.itemSnapshotList}\n ${pagingData.itemCount}"

    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.sort_kanji)
            )
            Text(
                text = stringResource(R.string.sort_hiragana)
            )
            Text(
                text = stringResource(R.string.sort_korean)
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(pagingData.itemCount) { index ->
                pagingData[index]?.let {
                    JapaneseWordListItem(
                        word = it
                    )
                }
            }
        }
    }

}