package com.example.domain.repository.repository

import androidx.paging.PagingData
import com.example.domain.repository.model.JapaneseWord
import kotlinx.coroutines.flow.Flow


interface StudyJapanese {

    fun getAllWordListByPage(): Flow<PagingData<JapaneseWord>>

}