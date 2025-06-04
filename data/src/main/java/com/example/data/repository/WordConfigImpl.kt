package com.example.data.repository

import com.example.database.dao.JapaneseDao
import com.example.domain.repository.repository.WordConfig
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WordConfigImpl @Inject constructor(
    private val japaneseDao: JapaneseDao
) : WordConfig {

    override fun getAllJapaneseIdList(): Flow<List<Long>> {
        return japaneseDao.getAllIdList()
    }

}