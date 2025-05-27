package com.example.domain.repository.repository

import com.example.domain.repository.model.JapaneseWord

interface SettingJapanese {

    suspend fun setJapaneseWord(word: JapaneseWord)

    suspend fun deleteJapaneseWord(word: JapaneseWord)
}