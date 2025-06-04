package com.example.domain.repository.usecase

import androidx.paging.PagingData
import com.example.domain.repository.model.JapaneseWord
import com.example.domain.repository.repository.StudyJapanese
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetJapaneseWordByKeywordUseCase @Inject constructor(
    private val japanese: StudyJapanese
) {
    operator fun invoke(keyword: String): Flow<PagingData<JapaneseWord>> {
        return japanese.getWordListByPageAndKeyWord(keyword)
    }
}