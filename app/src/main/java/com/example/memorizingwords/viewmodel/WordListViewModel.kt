package com.example.memorizingwords.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.domain.repository.model.JapaneseWord as wordDomain
import com.example.domain.repository.repository.StudyJapanese
import com.example.domain.repository.usecase.GetJapaneseWordByKeywordUseCase
import com.example.memorizingwords.mapper.toUI
import com.example.memorizingwords.model.JapaneseWord
import com.example.memorizingwords.state.WordListScreenState
import com.example.memorizingwords.trigger.JapaneseWordPagingRefreshTrigger
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordListViewModel @Inject constructor(
    application: Application,
    private val japaneseRepository: StudyJapanese,
    private val pagingTrigger: JapaneseWordPagingRefreshTrigger,
    private val getJapaneseWordByKeywordUseCase: GetJapaneseWordByKeywordUseCase,
) : AndroidViewModel(application) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
//            screenState.flatMapLatest {  }
        }
    }

    private var _screenState: MutableStateFlow<WordListScreenState> = MutableStateFlow(WordListScreenState())
    val screenState: StateFlow<WordListScreenState> = _screenState.asStateFlow()


    @OptIn(ExperimentalCoroutinesApi::class)
    val japanesePagingWordList: StateFlow<PagingData<JapaneseWord>> =
        pagingTrigger.refreshState.flatMapLatest {
            japaneseRepository.getAllWordListByPage()
                .map { pagingData ->
                    pagingData.map { wordDomain ->
                        wordDomain.toUI()
                    }
                }
        }
            .flowOn(Dispatchers.IO)
            .cachedIn(viewModelScope)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = PagingData.empty(),
            )

    private val japanesePagingWordKeyword: MutableStateFlow<String> = MutableStateFlow("")
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val japanesePagingWordListByKeyword: StateFlow<PagingData<JapaneseWord>> =
        combine(pagingTrigger.refreshState, japanesePagingWordKeyword.debounce(300)) { _, keyword ->
            keyword
        }.flatMapLatest { keyword ->
            getJapaneseWordByKeywordUseCase(keyword)
                .map { pagingData ->
                    pagingData.map { it.toUI() }
                }
        }
            .flowOn(Dispatchers.IO)
            .cachedIn(viewModelScope)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = PagingData.empty(),
            )


    fun onChangeKeyword(value: String) {
        japanesePagingWordKeyword.update {
            value
        }
        _screenState.update {
            it.copy(
                keyword = value
            )
        }
    }
}