package com.example.memorizingwords.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import com.example.memorizingwords.ui.components.EditText
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
    navigateToWordDetail: (id: Long) -> Unit = {},
    viewModel: WordListViewModel = hiltViewModel()
) {

    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val pagingWordList = viewModel.japanesePagingWordListByKeyword.collectAsLazyPagingItems()

    WordListScreen(
        modifier = modifier,
        onClickWordCard = navigateToWordDetail,
        onChangeKeyword = viewModel::onChangeKeyword,
        screenState = screenState,
        pagingData = pagingWordList
    )
}


@Composable
fun WordListScreen(
    modifier: Modifier = Modifier,
    onClickWordCard: (wordId: Long) -> Unit = {},
    onChangeKeywordType: () -> Unit = {},
    onChangeKeyword: (value: String) -> Unit = {},
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
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(screenState.searchType.id),
                fontWeight = FontWeight.Black,
                modifier = Modifier
                    .border(2.dp, Color.Black, RoundedCornerShape(5.dp))
                    .padding(10.dp)
                    .clickable { onChangeKeywordType() }
            )

            EditText(
                inputText = screenState.keyword,
                borderColor = Color.Gray,
                paddingHorizontal = 10.dp,
                paddingVertical = 10.dp,
                onValueChange = onChangeKeyword,
                modifier = Modifier
                    .padding(5.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

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

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(pagingData.itemCount) { index ->
                pagingData[index]?.let {
                    JapaneseWordListItem(
                        word = it,
                        modifier = Modifier
                            .height(50.dp)
                            .clickable {
                                onClickWordCard(it.id)
                            }
                    )
                }
            }
        }
    }

}