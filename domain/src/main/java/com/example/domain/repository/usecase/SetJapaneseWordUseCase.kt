package com.example.domain.repository.usecase

import com.example.domain.repository.model.JapaneseWord
import com.example.domain.repository.repository.SettingJapanese
import javax.inject.Inject

class SetJapaneseWordUseCase @Inject constructor(
    private val settingJapanese: SettingJapanese
) {
    suspend operator fun invoke(word: JapaneseWord) {
        settingJapanese.setJapaneseWord(word)
    }
}