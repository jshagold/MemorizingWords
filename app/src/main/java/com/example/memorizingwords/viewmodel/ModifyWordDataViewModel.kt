package com.example.memorizingwords.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.usecase.DeleteJapaneseWordUseCase
import com.example.domain.repository.usecase.GetJapaneseWordUseCase
import com.example.domain.repository.usecase.UpdateJapaneseWordUseCase
import com.example.memorizingwords.mapper.toDomain
import com.example.memorizingwords.mapper.toUI
import com.example.memorizingwords.model.JapaneseWord
import com.example.domain.repository.model.JapaneseWord as JapaneseWordDomain
import com.example.memorizingwords.model.PartOfSpeechType
import com.example.memorizingwords.state.ModifyWordDataScreenState
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
class ModifyWordDataViewModel @Inject constructor(
    application: Application,
    savedStateHandle: SavedStateHandle,
    private val getJapaneseWordUseCase: GetJapaneseWordUseCase,
    private val updateJapaneseWordUseCase: UpdateJapaneseWordUseCase,
    private val pagingTrigger: JapaneseWordPagingRefreshTrigger,
) : AndroidViewModel(application)  {

    private val routeArgument: String? = savedStateHandle["wordId"]
    private val wordId: Long? = routeArgument?.toLong()

    private var _screenState: MutableStateFlow<ModifyWordDataScreenState> = MutableStateFlow(ModifyWordDataScreenState())
    val screenState: StateFlow<ModifyWordDataScreenState> = _screenState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            wordId?.let { wordId ->
                getJapaneseWordUseCase(wordId).collectLatest { word: JapaneseWordDomain? ->
                    withContext(Dispatchers.Main) {
                        _screenState.update {
                            it.copy(
                                word = word?.toUI()
                            )
                        }
                    }
                }
            }
        }
    }


    fun onChangeKanji(value: String) {
        val changedWord = screenState.value.word?.copy(
            kanji = value
        )
        Log.e("TAG", "onChangeKanji: $changedWord", )
        _screenState.update {
            it.copy(
                word = changedWord
            )
        }
    }

    fun onChangeHiragana(value: String) {
        _screenState.update {
            it.copy(
                word = it.word?.copy(hiragana = value)
            )
        }
    }

    fun onChangeKorean(value: String) {
        _screenState.update {
            it.copy(
                word = it.word?.copy(korean = listOf(value))
            )
        }
    }

    fun onChangePOS(index: Int) {
        _screenState.update {
            it.copy(
                word = it.word?.copy(partOfSpeech = PartOfSpeechType.entries[index])
            )
        }
    }

    fun onChangeExample(value: String) {
        _screenState.update {
            it.copy(
                word = it.word?.copy(exampleSentence = listOf(value))
            )
        }
    }

    fun onChangeFavorite() {
        _screenState.update {
            it.copy(
                word = it.word?.copy(isFavorite = !it.word.isFavorite)
            )
        }
    }

    fun onModifyBtn(callback: () -> Unit) {
        val word = screenState.value.word

        viewModelScope.launch(Dispatchers.IO) {
            word?.let {
                updateJapaneseWordUseCase(word.toDomain())

                pagingTrigger.notifyRefresh()
                withContext(Dispatchers.Main) {
                    callback()
                }
            }
        }
    }
}