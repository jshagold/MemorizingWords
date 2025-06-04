package com.example.domain.repository.usecase

import androidx.paging.PagingData
import com.example.domain.repository.model.JapaneseWord
import com.example.domain.repository.repository.StudyJapanese
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetJapaneseRandomWordListUseCase @Inject constructor(
    private val studyJapanese: StudyJapanese
) {
    operator fun invoke(idList: List<Long>): Flow<PagingData<JapaneseWord>> {
        return studyJapanese.getRandomWordList(idList)
    }
}