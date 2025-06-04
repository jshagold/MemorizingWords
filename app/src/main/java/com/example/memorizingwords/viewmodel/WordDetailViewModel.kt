package com.example.memorizingwords.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.usecase.DeleteJapaneseWordUseCase
import com.example.domain.repository.usecase.GetJapaneseWordByIdUseCase
import com.example.memorizingwords.mapper.toDomain
import com.example.memorizingwords.mapper.toUI
import com.example.domain.repository.model.JapaneseWord as JapaneseWordDomain
import com.example.memorizingwords.state.WordDetailScreenState
import com.example.memorizingwords.trigger.JapaneseWordPagingRefreshTrigger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WordDetailViewModel @Inject constructor(
    application: Application,
    savedStateHandle: SavedStateHandle,
    private val getJapaneseWordByIdUseCase: GetJapaneseWordByIdUseCase,
    private val deleteJapaneseWordUseCase: DeleteJapaneseWordUseCase,
    private val pagingTrigger: JapaneseWordPagingRefreshTrigger,
) : AndroidViewModel(application) {

    private val routeArgument: String? = savedStateHandle["wordId"]
    private val wordId: Long? = routeArgument?.toLong()

    private var _screenState: MutableStateFlow<WordDetailScreenState> = MutableStateFlow(WordDetailScreenState())
    val screenState: StateFlow<WordDetailScreenState> = _screenState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            wordId?.let { wordId ->
                getJapaneseWordByIdUseCase(wordId).collectLatest { word: JapaneseWordDomain? ->
                    withContext(Dispatchers.Main) {
                        word?.let {
                            _screenState.update {
                                it.copy(
                                    word = word.toUI()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun deleteWord(
        callback: () -> Unit = {}
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            screenState.value.word?.let {
                deleteJapaneseWordUseCase(it.toDomain())
                pagingTrigger.notifyRefresh()

                withContext(Dispatchers.Main) {
                    callback()
                }
            }
        }
    }

}