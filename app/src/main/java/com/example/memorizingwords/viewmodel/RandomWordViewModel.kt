package com.example.memorizingwords.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.usecase.GetJapaneseAllIdListUseCase
import com.example.domain.repository.usecase.GetJapaneseWordByIdUseCase
import com.example.memorizingwords.mapper.toUI
import com.example.memorizingwords.model.JapaneseWordType
import com.example.memorizingwords.state.RandomWordScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RandomWordViewModel @Inject constructor(
    application: Application,
    private val getJapaneseAllIdListUseCase: GetJapaneseAllIdListUseCase,
    private val getJapaneseWordByIdUseCase: GetJapaneseWordByIdUseCase,
) : AndroidViewModel(application) {

    private lateinit var randomIdList: List<Long>

    private var _screenState: MutableStateFlow<RandomWordScreenState> = MutableStateFlow(RandomWordScreenState())
    val screenState: StateFlow<RandomWordScreenState> = _screenState.asStateFlow()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                randomIdList = getJapaneseAllIdListUseCase().first().shuffled()
            }

            withContext(Dispatchers.IO) {
                val initWord = getJapaneseWordByIdUseCase(randomIdList[screenState.value.index]).first()
                initWord?.let { word ->
                    _screenState.update {
                        it.copy(
                            word = word.toUI()
                        )
                    }
                }
            }

        }
    }

    fun onClickTextCard() {
        val type = when(screenState.value.type) {
            JapaneseWordType.KANJI -> JapaneseWordType.HIRAGANA
            JapaneseWordType.HIRAGANA -> JapaneseWordType.KOREAN
            JapaneseWordType.KOREAN -> JapaneseWordType.KANJI
        }
        _screenState.update {
            it.copy(
                type = type
            )
        }
    }

    fun onClickBackBtn() {
        viewModelScope.launch(Dispatchers.IO) {
            val index = if(screenState.value.index <= 0) {
                0
            } else {
                screenState.value.index - 1
            }

            val beforeWord = getJapaneseWordByIdUseCase(randomIdList[index]).first()
            beforeWord?.let { word ->
                withContext(Dispatchers.Main) {
                    _screenState.update {
                        it.copy(
                            index = index,
                            word = word.toUI()
                        )
                    }
                }
            }
        }
    }


    fun onClickNextBtn() {
        viewModelScope.launch(Dispatchers.IO) {
            val index = if(screenState.value.index >= randomIdList.lastIndex) {
                0
            } else {
                screenState.value.index + 1
            }

            val nextWord = getJapaneseWordByIdUseCase(randomIdList[index]).first()
            nextWord?.let { word ->
                withContext(Dispatchers.Main) {
                    _screenState.update {
                        it.copy(
                            index = index,
                            word = word.toUI()
                        )
                    }
                }
            }
        }
    }



}