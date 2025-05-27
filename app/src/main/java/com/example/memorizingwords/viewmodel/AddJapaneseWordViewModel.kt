package com.example.memorizingwords.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.model.JapaneseWord as WordDomain
import com.example.domain.repository.repository.SettingJapanese
import com.example.domain.repository.usecase.SetJapaneseWordUseCase
import com.example.memorizingwords.state.AddJapaneseScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddJapaneseWordViewModel @Inject constructor(
    application: Application,
    private val setJapaneseWordUseCase: SetJapaneseWordUseCase,
) : AndroidViewModel(application) {

    private var _screenState: MutableStateFlow<AddJapaneseScreenState> = MutableStateFlow(AddJapaneseScreenState())
    val screenState: StateFlow<AddJapaneseScreenState> = _screenState.asStateFlow()


    fun changeKanji(kanji: String) {
        _screenState.update {
            it.copy(
                kanji = kanji
            )
        }
    }

    fun changeHiragana(hiragana: String) {
        _screenState.update {
            it.copy(
                hiragana = hiragana
            )
        }
    }

    fun changeKorean(korean: String) {
        _screenState.update {
            it.copy(
                korean = korean
            )
        }
    }


    fun addNewWord() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                screenState.value.hiragana?.let { hiragana ->
                    val word = WordDomain(
                        kanji = screenState.value.kanji,
                        hiragana = hiragana,
                        korean = listOf(screenState.value.korean),
                        partOfSpeech = 0,
                        exampleSentence = listOf(),
                        createdAt = System.currentTimeMillis(),
                        lastStudiedAt = System.currentTimeMillis()
                    )

                    setJapaneseWordUseCase(word)
                }
            }

            _screenState.update {
                AddJapaneseScreenState()
            }
        }
    }

}