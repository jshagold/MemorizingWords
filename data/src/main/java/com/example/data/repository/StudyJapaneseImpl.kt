package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.constants.Paging.PAGE_SIZE
import com.example.data.datasource.JapanesePagingSource
import com.example.database.dao.JapaneseDao
import com.example.domain.repository.model.JapaneseWord
import com.example.domain.repository.repository.StudyJapanese
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StudyJapaneseImpl @Inject constructor(
    private val japaneseDao: JapaneseDao
) : StudyJapanese {
    override fun getAllWordListByPage(): Flow<PagingData<JapaneseWord>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { JapanesePagingSource(japaneseDao) }
        ).flow
            .flowOn(Dispatchers.IO)
    }
}