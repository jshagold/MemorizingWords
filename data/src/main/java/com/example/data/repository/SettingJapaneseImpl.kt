package com.example.data.repository

import com.example.data.mapper.toData
import com.example.data.mapper.toEntity
import com.example.database.dao.JapaneseDao
import com.example.domain.repository.model.JapaneseWord
import com.example.domain.repository.repository.SettingJapanese
import javax.inject.Inject

class SettingJapaneseImpl @Inject constructor(
    private val japaneseDao: JapaneseDao
) : SettingJapanese {
    override suspend fun setJapaneseWord(word: JapaneseWord) {
        japaneseDao.upsertWord(word.toData().toEntity())
    }

    override suspend fun deleteJapaneseWord(word: JapaneseWord) {
        japaneseDao.deleteWord(word.toData().toEntity())
    }
}